package com.damian.weightliftingtracker.feature_exercise.presentation.add_edit_exercise

sealed class AddEditExerciseEvent {
    data class OnEnterName(val value: String) : AddEditExerciseEvent()
    data class OnEnterDescription(val value: String) : AddEditExerciseEvent()
    object OnSaveSession : AddEditExerciseEvent()
    object OnCloseClick : AddEditExerciseEvent()
}
