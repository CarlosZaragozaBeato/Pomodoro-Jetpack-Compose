package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.repository

import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro

interface PomodoroRepository {

    fun getPomodoros(): Pomodoro?

    suspend fun insertPomodoro(pomodoro: Pomodoro)

}