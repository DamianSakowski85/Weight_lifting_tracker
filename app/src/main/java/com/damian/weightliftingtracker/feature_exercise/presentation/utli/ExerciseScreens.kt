package com.damian.weightliftingtracker.feature_exercise.presentation.utli

const val EXERCISE_SCREEN_ROUTE = "exercise_screen"
const val ADD_EDIT_EXERCISE_ROUTE = "add_edit_exercise"

sealed class ExerciseScreens(val route: String) {
    object ExerciseScreen : ExerciseScreens(EXERCISE_SCREEN_ROUTE)
    object AddEditExerciseScreen : ExerciseScreens(ADD_EDIT_EXERCISE_ROUTE)
}