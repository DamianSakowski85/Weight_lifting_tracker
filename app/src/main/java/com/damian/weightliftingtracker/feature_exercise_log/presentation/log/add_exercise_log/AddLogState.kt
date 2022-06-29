package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log

data class AddLogState(
    val exerciseId: Int? = 0,
    val exerciseName: String = "",
    val weightGoal: String = "",
    val repsGoal: String = "",
    val pauseGoal: String = "",
    val weightAchieved: String = "",
    val repsAchieved: String = "",
    val pauseAchieved: String = "",
    val isTimerVisible : Boolean = false,
    val pauseTimerValue : String = "",
    val isCompleteSetActive : Boolean = true
)
