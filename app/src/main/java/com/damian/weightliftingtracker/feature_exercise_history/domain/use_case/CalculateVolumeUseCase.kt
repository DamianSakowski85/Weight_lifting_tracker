package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.LogVolume

class CalculateVolumeUseCase {
    operator fun invoke(exercisesLog: List<ExerciseLog>): LogVolume {
        var weightVolume = 0.0
        var pauseVolume = 0

        exercisesLog.forEach {
            weightVolume += (it.repsAchieved * it.weightAchieved)
            pauseVolume += it.pauseAchieved
        }

        return LogVolume(
            weightVolume = weightVolume.toString(),
            pauseVolume = pauseVolume.toString()
        )
    }
}