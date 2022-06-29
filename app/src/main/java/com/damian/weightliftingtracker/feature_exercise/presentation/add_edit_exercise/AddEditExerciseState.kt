package com.damian.weightliftingtracker.feature_exercise.presentation.add_edit_exercise

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise

data class AddEditExerciseState(
    val exerciseToEdit: Exercise? = null,
    val sessionId: Int? = null,
    val name: String = "",
    val description: String = ""
)