package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.use_cases

import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.repository.PomodoroRepository
import javax.inject.Inject

class GetPomodoroUseCase @Inject constructor(
    private val repository: PomodoroRepository
) {
    operator fun invoke(): Pomodoro? {
        return repository.getPomodoros()
    }
}