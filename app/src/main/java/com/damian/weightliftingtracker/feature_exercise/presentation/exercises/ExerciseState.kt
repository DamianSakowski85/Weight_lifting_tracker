package com.damian.weightliftingtracker.feature_exercise.presentation.exercises

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise

data class ExerciseState constructor(
    val sessionId: Int = -1,
    val sessionName: String = "",
    val exercises: List<Exercise> = emptyList(),
    val selectedExercise: Exercise? = null,
    val isDeleteDialogVisible: Boolean = false,
    val isClearHistoryDialogVisible: Boolean = false,
    val isEmptyListLabelVisible: Boolean = false,
)
