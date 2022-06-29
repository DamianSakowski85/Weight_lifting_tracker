package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker


sealed class ExerciseHistoryEvent {
    object OnBackPressed : ExerciseHistoryEvent()
    object OnPickDate : ExerciseHistoryEvent()
    data class OnDateClick(val date: String) : ExerciseHistoryEvent()
}
