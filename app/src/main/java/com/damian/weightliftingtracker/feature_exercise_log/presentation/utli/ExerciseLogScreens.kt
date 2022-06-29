package com.damian.weightliftingtracker.feature_exercise_log.presentation.utli

const val EXERCISE_LOG_SCREEN_ROUTE_KEY = "exercise_log_screen"

sealed class ExerciseLogScreens(val route: String) {
    object ExerciseLogScreen : ExerciseLogScreens(EXERCISE_LOG_SCREEN_ROUTE_KEY)
}