package com.damian.weightliftingtracker.feature_exercise.presentation.exercises

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise


sealed class ExerciseEvent {
    object OnAdd : ExerciseEvent()
    data class OnExerciseClick(val exercise: Exercise) : ExerciseEvent()
    object OnConfirmDeletion : ExerciseEvent()
    object OnDismissDialog : ExerciseEvent()
    object OnBackPressed : ExerciseEvent()
    data class OnMenuClick(val exercise: Exercise) : ExerciseEvent()
    object OnEdit : ExerciseEvent()
    object OnDelete : ExerciseEvent()
    object OnShowBarCharts : ExerciseEvent()
    object OnShowDatePicker : ExerciseEvent()
    object OnClearExerciseData : ExerciseEvent()
    object OnConfirmDataClearance : ExerciseEvent()
    object OnDismissDataClearance : ExerciseEvent()
}
