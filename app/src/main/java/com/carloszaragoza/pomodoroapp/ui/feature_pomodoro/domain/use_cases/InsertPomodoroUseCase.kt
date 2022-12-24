package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.use_cases

import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.repository.PomodoroRepository
import javax.inject.Inject

class InsertPomodoroUseCase  @Inject constructor(
    private val repository: PomodoroRepository
){

    suspend operator fun invoke(pomodoro:Pomodoro){
        repository.insertPomodoro(pomodoro)
    }
}