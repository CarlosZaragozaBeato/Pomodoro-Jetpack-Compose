package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarHome() {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        title = {
            Text("pomodoro",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground.copy(.8f),
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
    )
}