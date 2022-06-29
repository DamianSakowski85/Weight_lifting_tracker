package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.previous_logs

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog

data class PreviousLogsState(
    val isPreviousLogEmpty: Boolean = true,
    val isVolumeVisible: Boolean = false,
    val exerciseId: Int? = 0,
    val previousDate: String = "",
    val logs: List<ExerciseLog> = emptyList(),
    val weightVolume: String = "0.0",
    val pauseVolume: String = "0"
)
