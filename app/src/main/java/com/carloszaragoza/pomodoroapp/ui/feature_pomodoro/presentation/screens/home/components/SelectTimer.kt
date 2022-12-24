package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carloszaragoza.pomodoroapp.ui.theme.LocalSpacing

@Composable
fun SelectTimer(
    currentSelected:String,
    currentColor:Int,
    onChangeOption: (String) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(top = LocalSpacing.current.medium),
        contentAlignment = Alignment.Center){
            LazyRow(modifier = Modifier
                .fillMaxWidth(.95f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(LocalSpacing.current.large))
                .background(MaterialTheme.colors.onBackground.copy(.05f))
                .shadow(elevation = LocalSpacing.current.large),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                items( TimerOption.listOfOptions){ option ->
                    TimerItem(option = option.option,
                        selected = option.option == currentSelected,
                        currentColor = currentColor){ option ->
                        onChangeOption.invoke(option)
                    }
            }
        }
    }
}
@Composable
fun TimerItem(
    option:String,
    selected: Boolean,
    currentColor:Int,
    onChangeOption:(String) -> Unit) {
    Box(modifier = Modifier
        .width(120.dp)
        .fillMaxHeight()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ){
            onChangeOption.invoke(option)
        }
        .padding(LocalSpacing.current.small)
        .clip(CircleShape)
        .background(
            if (selected)
                Color(currentColor)
            else
                Color.Transparent),
        contentAlignment = Alignment.Center){
        Text(option,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if(selected)
                        MaterialTheme.colors.background
                    else
                        MaterialTheme.colors.onBackground.copy(.5f),
            modifier = Modifier
                .padding(LocalSpacing.current.small))
    }

}

class TimerOption(val option:String){
     companion object{
         val listOfOptions = listOf<TimerOption>(
             TimerOption(option = "pomodoro"),
             TimerOption(option = "short break"),
             TimerOption(option = "long break"))
     }

 }

