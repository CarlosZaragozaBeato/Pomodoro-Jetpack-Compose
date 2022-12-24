package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro

@Dao
interface PomodoroDao {

    @Query("SELECT * FROM pomodoro_tbl")
    fun getPomodoro(): Pomodoro?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPomodoro(pomodoro:Pomodoro)
}