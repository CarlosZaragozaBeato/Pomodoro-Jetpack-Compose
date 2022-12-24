package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pomodoro_tbl")
data class Pomodoro(
    @PrimaryKey
    val id:Int? = null,
    val time:Int,
    val shortBreak: Int? = 5,
    val longBreak: Int? = 15,
    val color: Int
)
