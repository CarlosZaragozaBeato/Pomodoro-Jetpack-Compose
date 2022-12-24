package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.data.repository

import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.data.data_source.PomodoroDao
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.repository.PomodoroRepository
import javax.inject.Inject

class PomodoroRepositoryImp @Inject constructor(
    private val dao : PomodoroDao
): PomodoroRepository {
    override fun getPomodoros(): Pomodoro? {
        return dao.getPomodoro()
    }

    override suspend fun insertPomodoro(pomodoro: Pomodoro) {
        dao.addPomodoro(pomodoro = pomodoro)
    }
}