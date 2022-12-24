package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.util

sealed class UiEvent{
    data class ShowSnackBar(val message:String):UiEvent()
}
