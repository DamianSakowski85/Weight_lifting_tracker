package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.current_logs

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog

data class CurrentLogsState(
    val isCurrentLogEmpty: Boolean = true,
    val isVolumeVisible: Boolean = false,
    val exerciseId: Int? = 0,
    val logs: List<ExerciseLog> = emptyList(),
    val currentLogToDelete: ExerciseLog? = null,
    val isDeleteDialogVisible: Boolean = false,
    val weightVolume: String = "0.0",
    val pauseVolume: String = "0"
)
