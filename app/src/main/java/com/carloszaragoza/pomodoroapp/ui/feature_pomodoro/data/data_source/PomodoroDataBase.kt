package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro

@Database(
    entities = [Pomodoro::class],
    version = 1
)
abstract class PomodoroDataBase:RoomDatabase() {
    abstract val dao: PomodoroDao

    companion object{
        const val database_name = "POMODORO_DB"
    }
}