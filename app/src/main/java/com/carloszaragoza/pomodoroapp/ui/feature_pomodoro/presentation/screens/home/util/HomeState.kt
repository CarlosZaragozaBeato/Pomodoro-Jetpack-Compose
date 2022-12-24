package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro

data class HomeState(
    val pomodoro: MutableState<Pomodoro?> = mutableStateOf(null),
    val currentOption: MutableState<String?> = mutableStateOf("pomodoro"),
    val currentMin: MutableState<Int?> = mutableStateOf(null),
    val currentSec: MutableState<Int> = mutableStateOf(0),
    val currentShortMin: MutableState<Int?> = mutableStateOf(null),
    val currentShortSec: MutableState<Int> = mutableStateOf(0),
    val currentLongMin: MutableState<Int?> = mutableStateOf(null),
    val currentLongSec: MutableState<Int> = mutableStateOf(0),
    val isLoading:MutableState<Boolean> = mutableStateOf(false),
    val timerState: MutableState<Float> = mutableStateOf(150f),
    val isOpen: MutableState<Boolean> = mutableStateOf(false),
    val optionsPomodoroMinutes: MutableState<Int?> = mutableStateOf(null),
    val optionsShortMinutes: MutableState<Int?> = mutableStateOf(null),
    val optionsLongMinutes: MutableState<Int?> = mutableStateOf(null),
    val colorSelected: MutableState<Int?> = mutableStateOf(null),
    val currentState: MutableState<String> = mutableStateOf(State.INITIAL_STATE.name),
    val currentWidthPomodoro: MutableState<Float> = mutableStateOf(0f),
    val currentWidthShortBreak: MutableState<Float> = mutableStateOf(0f),
    val currentWidthLongBreak: MutableState<Float> = mutableStateOf(0f))
