package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util

sealed class HomeEvent{
    data class ChangeOption(val option:String): HomeEvent()
    object OpenOptions: HomeEvent()
    object PlusMinutePomodoro: HomeEvent()
    object MinusMinutePomodoro: HomeEvent()
    object PlusMinuteShortBreak: HomeEvent()
    object MinusMinuteShortBreak: HomeEvent()
    object PlusMinuteLongBreak: HomeEvent()
    object MinusMinuteLongBreak: HomeEvent()
    data class ChangeColor(val color:Int):HomeEvent()
    object OnChange:HomeEvent()
    object InitPomodoro: HomeEvent()
    object InitShortBreak: HomeEvent()
    object InitLongBreak: HomeEvent()
}
