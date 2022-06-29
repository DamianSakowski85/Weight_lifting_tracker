package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.current_logs

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog

sealed class CurrentExerciseLogEvent {
    data class OnDelete(val log: ExerciseLog) : CurrentExerciseLogEvent()
    object OnConfirmDeletion : CurrentExerciseLogEvent()
    object OnDismissDialog : CurrentExerciseLogEvent()
}