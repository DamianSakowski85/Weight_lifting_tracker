package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log

sealed class AddLogEvent {

    data class OnEnterWeightGoal(val value: String) : AddLogEvent()
    object OnIncreaseWeightGoalValue : AddLogEvent()
    object OnDecreaseWeightGoalValue : AddLogEvent()
    data class OnEnterRepsGoal(val value: String) : AddLogEvent()
    object OnIncreaseRepsGoalValue : AddLogEvent()
    object OnDecreaseRepsGoalValue : AddLogEvent()
    data class OnEnterPauseGoal(val value: String) : AddLogEvent()
    object OnIncreasePauseGoalValue : AddLogEvent()
    object OnDecreasePauseGoalValue : AddLogEvent()

    data class OnEnterWeightAchieved(val value: String) : AddLogEvent()
    object OnIncreaseWeightAchievedValue : AddLogEvent()
    object OnDecreaseWeightAchievedValue : AddLogEvent()
    data class OnEnterRepsAchieved(val value: String) : AddLogEvent()
    object OnIncreaseRepsAchievedValue : AddLogEvent()
    object OnDecreaseRepsAchievedValue : AddLogEvent()
    data class OnEnterPauseAchieved(val value: String) : AddLogEvent()
    object OnIncreasePauseAchievedValue : AddLogEvent()
    object OnDecreasePauseAchievedValue : AddLogEvent()

    object OnSave : AddLogEvent()
    object OnClose : AddLogEvent()

    data class UpdateTimer(val timerValue : Long) : AddLogEvent()
    object PauseFinished : AddLogEvent()
}
