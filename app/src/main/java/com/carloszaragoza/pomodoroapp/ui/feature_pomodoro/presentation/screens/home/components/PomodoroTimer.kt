package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util.State
import com.carloszaragoza.pomodoroapp.ui.theme.LocalSpacing

@Composable
fun PomodoroTimer(
    currentMin: MutableState<Int?>,
    currentSec: MutableState<Int>,
    currentColor: Int,
    state:String,
    timerState: MutableState<Float>,
    onResume: () -> Unit){

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.7f)
        .clickable{ onResume.invoke() },
        contentAlignment = Alignment.Center){
       Card(modifier = Modifier
           .fillMaxWidth(.8f)
           .fillMaxHeight(.8f),
        shape = CircleShape,
        elevation = LocalSpacing.current.extraLarge,
       backgroundColor = MaterialTheme.colors.background.copy(.8f),){
        Box(modifier = Modifier.
            fillMaxSize()
                .drawBehind {
                    drawArc(
                        color = Color(currentColor),
                        startAngle = -90f,
                        sweepAngle = timerState.value,
                        useCenter = false,
                        topLeft = Offset(35f,55f),
                        size = Size(795f,800f),
                        style = Stroke(
                            width = 8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
        ){

       }
           Column(
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center,
                      modifier = Modifier
                          .padding(LocalSpacing.current.medium)
                  ) {
                      Text(buildAnnotatedString {
                          withStyle(style = SpanStyle(
                              fontSize = 70.sp,
                              fontWeight = FontWeight.Bold,
                              color = MaterialTheme.colors.onBackground.copy(.9f))
                          ){
                              withStyle(style = SpanStyle()){
                                  append(currentMin.value.toString())
                              }
                              withStyle(style = SpanStyle()){
                                  append(":")
                              }
                              withStyle(style = SpanStyle()){
                                  append(currentSec.value.toString())
                              }
                          }
                      })
                      Text(if(state == State.PAUSE_POMODORO.name||
                              state == State.PAUSE_SHORT_BREAK.name||
                              state == State.PAUSE_LONG_BREAK.name)
                          "P A U S E"
                      else
                          "R E S U M E"
                          ,style = TextStyle(
                              fontSize = 15.sp,
                              textAlign = TextAlign.Center,
                              fontWeight = FontWeight.SemiBold),
                          modifier = Modifier.fillMaxWidth()
                      )
                  }
              }
    }

}