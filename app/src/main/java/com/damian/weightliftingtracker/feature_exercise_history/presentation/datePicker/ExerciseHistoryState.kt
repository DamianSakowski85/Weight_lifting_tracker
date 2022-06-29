package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker

import com.damian.weightliftingtracker.feature_exercise_history.domain.model.CalendarConstraintsModel
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog

data class ExerciseHistoryState(
    val exerciseId: Int? = 0,
    val exerciseName: String = "",
    val isLogEmpty: Boolean = true,
    val logs: List<ExerciseLog> = emptyList(),
    val date: String = "",
    val weightVolume: String = "0.0",
    val pauseVolume: String = "0",
    val calendarConstraintsModel: CalendarConstraintsModel? = null
)
