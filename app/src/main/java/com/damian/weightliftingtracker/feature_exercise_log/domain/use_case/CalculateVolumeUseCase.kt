package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

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

        /*
        if (pauseVolume != 0){
            val pauseDecimal = pauseVolume / 60
            val pauseValueList = pauseDecimal.toString().split(".")
            val pauseMin = pauseValueList.first()
            val secDecimalValue = (pauseValueList.last().toInt() * 0.01)
            val pauseSec = secDecimalValue.toInt() * 60

            minutesVolume = "$pauseMin min. $pauseSec sec."
        }
         */

        return LogVolume(
            weightVolume = weightVolume.toString(),
            pauseVolume = pauseVolume.toString()
        )
    }
}