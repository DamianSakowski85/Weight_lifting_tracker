package com.damian.weightliftingtracker.feature_exercise_history.presentation.utli

const val DATE_PICKER_SCREEN_ROUTE = "date_picker_screen"

sealed class DatePickerScreens(val route: String) {
    object DatePickerScreen : DatePickerScreens(DATE_PICKER_SCREEN_ROUTE)
}
