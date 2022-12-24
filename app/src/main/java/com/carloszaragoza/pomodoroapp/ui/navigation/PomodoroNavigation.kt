package com.carloszaragoza.pomodoroapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.HomeScreen

@Composable
fun PomodoroNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.HOME.name){
        composable(route = Routes.HOME.name) {
            HomeScreen()
        }
    }
}