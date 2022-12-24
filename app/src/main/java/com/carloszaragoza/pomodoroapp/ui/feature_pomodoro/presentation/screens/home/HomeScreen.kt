package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.components.PomodoroTimer
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.components.SelectTimer
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.components.TopAppBarHome
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util.HomeEvent
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util.HomeState
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.util.UiEvent
import com.carloszaragoza.pomodoroapp.ui.theme.LocalSpacing
import com.carloszaragoza.pomodoroapp.ui.theme.listOfColors

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message)
                }
            }
        }
    }
    val state = viewModel.state.collectAsState().value
    if(!state.isLoading.value){
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = MaterialTheme.colors.background,
            topBar = { TopAppBarHome() }){
        if(state.isOpen.value){
            Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
                PomodoroOptions(
                    viewModel = viewModel,
                    state = state
                )
        }
    }else{
            MainTimer(state = state,
                        viewModel = viewModel)
        }
    }}else{
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                color = listOfColors.first()
            )
        }
    }
}
@Composable
fun PomodoroOptions(
    viewModel: HomeViewModel = hiltViewModel(),
    state: HomeState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(.9f)
            .clip(RoundedCornerShape(LocalSpacing.current.medium))
            .background(MaterialTheme.colors.onBackground)
            .padding(bottom = LocalSpacing.current.medium),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally){

        Column {
            ModalTitle(modifier = Modifier){
                viewModel.onEvent(HomeEvent.OpenOptions)
            }

            Text("Time (Minutes)".uppercase(),
                color = MaterialTheme.colors.background,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = LocalSpacing.current.small,
                        bottom = LocalSpacing.current.large
                    ),
                textAlign = TextAlign.Center)
            ItemController(
                time = state.optionsPomodoroMinutes.value!!,
                title = "pomodoro",
                minusMinute = {
                    viewModel.onEvent(HomeEvent.MinusMinutePomodoro)
                },
                plusMinute = {
                    viewModel.onEvent(HomeEvent.PlusMinutePomodoro)
                },)
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
            ItemController(
                time = state.optionsShortMinutes.value!!,
                title = "short break",
                minusMinute = {
                    viewModel.onEvent(HomeEvent.MinusMinuteShortBreak)
                },
                plusMinute = {
                    viewModel.onEvent(HomeEvent.PlusMinuteShortBreak)
                },)
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
            ItemController(
                time = state.optionsLongMinutes.value!!,
                title = "long break",
                minusMinute = {
                    viewModel.onEvent(HomeEvent.MinusMinuteLongBreak)
                },
                plusMinute = {
                    viewModel.onEvent(HomeEvent.PlusMinuteLongBreak)
                })

            ColorPicker(
                colorSelected = state.colorSelected.value!!
            ){
                viewModel.onEvent(HomeEvent.ChangeColor(it))
            }

        }
        ApplyButton(currentColor = state.colorSelected.value!!){
            viewModel.onEvent(HomeEvent.OnChange)
        }
    }
}
@Composable
fun ApplyButton(
    currentColor: Int,
    apply:() -> Unit
) {
    Button( onClick = {apply.invoke()},
        modifier = Modifier
            .width(150.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(currentColor)
        )){
        Text("Apply",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xffffffff))
    }
}
@Composable
fun ColorPicker(
    colorSelected : Int,
    colorChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = LocalSpacing.current.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("COLOR",
            fontSize = 15.sp,
            color = MaterialTheme.colors.background,
            fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LocalSpacing.current.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
        listOfColors.forEach { color ->
               Box(modifier = Modifier
                   .size(60.dp)
                   .clickable {
                       colorChange.invoke(color.toArgb())
                   }
                   .clip(CircleShape)
                   .background(color),
                   contentAlignment = Alignment.Center){
                   if(colorSelected == color.toArgb()){
                       Icon(imageVector = Icons.Default.Check,
                           contentDescription = "Color selected")
                   }
               }
           }

        }
    }
}
@Composable
fun ItemController(
    title:String,
    time:Int,
    plusMinute: () -> Unit,
    minusMinute: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = LocalSpacing.current.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title,
            color = MaterialTheme.colors.background.copy(.5f),
            fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier
                .width(130.dp)
                .height(50.dp)
                .background(MaterialTheme.colors.background.copy(.05f)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(time.toString(),
                color = MaterialTheme.colors.background,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = LocalSpacing.current.medium))
            Row(
                horizontalArrangement = Arrangement.End
            ){

                    Icon(imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Plus one minute",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier
                            .clickable{
                                plusMinute.invoke()
                            })

                Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Minus one minute",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier
                            .clickable{
                                minusMinute.invoke()
                            })
            }
        }
    }
}
@Composable
fun ModalTitle(modifier: Modifier = Modifier,
            Close: () -> Unit) {
    Row(modifier = modifier
        .padding(LocalSpacing.current.medium)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Text("Settings",
            style = TextStyle(
                color = MaterialTheme.colors.background,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold))
        IconButton(onClick = { Close.invoke() }) {
            Icon(imageVector = Icons.Default.Close,
                contentDescription = "Close Modal",
                tint = MaterialTheme.colors.background)
        }
    }
}
@Composable
fun MainTimer(state: HomeState,
            viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        SelectTimer(
            currentColor = state.pomodoro.value?.color!! ,
            currentSelected = state.currentOption.value!!){ option ->
            viewModel.onEvent(HomeEvent.ChangeOption(option)) }
        PomodoroTimer(
            currentColor = state.pomodoro.value!!.color,
            currentMin = when(state.currentOption.value){
                "pomodoro" -> state.currentMin
                "short break" -> state.currentShortMin
                "long break" -> state.currentLongMin
                else -> state.currentMin },
            currentSec =  when(state.currentOption.value){
                "pomodoro" -> state.currentSec
                "short break" -> state.currentShortSec
                "long break" -> state.currentLongSec
                else -> state.currentSec },
            state = state.currentState.value,
            timerState = when(state.currentOption.value) {
                    "pomodoro" -> state.currentWidthPomodoro
                    "short break" -> state.currentWidthShortBreak
                    "long break" -> state.currentWidthLongBreak
                else -> { state.currentWidthPomodoro}
            },
            onResume = {
                when(state.currentOption.value) {
                    "pomodoro" -> viewModel.onEvent(HomeEvent.InitPomodoro)
                    "short break" -> viewModel.onEvent(HomeEvent.InitShortBreak)
                    "long break" -> viewModel.onEvent(HomeEvent.InitLongBreak) }

            })
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            IconButton(onClick = {
                viewModel.onEvent(HomeEvent.OpenOptions)
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Go to settings",
                    modifier = Modifier
                        .size(50.dp),
                    tint = MaterialTheme.colors.onBackground.copy(.5f)
                )
            }
        }
    }
}
