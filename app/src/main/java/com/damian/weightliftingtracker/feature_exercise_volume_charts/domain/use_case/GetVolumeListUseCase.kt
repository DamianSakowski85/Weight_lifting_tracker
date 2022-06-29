package com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.repository.ExerciseVolumeRepository
import com.madrapps.plot.line.DataPoint

class GetVolumeListUseCase(
    private val exerciseVolumeRepository: ExerciseVolumeRepository
) {
    suspend operator fun invoke(exerciseId: Int): List<List<DataPoint>> {
        val dates = exerciseVolumeRepository.getDates(exerciseId)
        val weightVolume = ArrayList<DataPoint>()
        val pauseVolume = ArrayList<DataPoint>()

        weightVolume.add(DataPoint(0f, 0f))
        pauseVolume.add(DataPoint(0f, 0f))

        var valueIndex = 1
        dates.forEach { date ->
            val exerciseSession = exerciseVolumeRepository.getSession(exerciseId, date)

            var weight = 0.0
            var pause = 0

            exerciseSession.forEach { set ->
                if (set.weightAchieved != 0.0 && set.repsAchieved != 0) {
                    weight += set.repsAchieved * set.weightAchieved
                }
                if (set.pauseAchieved != 0) {
                    pause += set.pauseAchieved
                }
            }

            if (weight != 0.0) {
                weightVolume.add(DataPoint(valueIndex.toFloat(), weight.toFloat()))
            }

            if (pause != 0) {
                pauseVolume.add(DataPoint(valueIndex.toFloat(), pause.toFloat()))
            }

            valueIndex++
        }


        /*
        val dataPoints1 = listOf(
            //DataPoint(0f,0f),
            DataPoint(1f, 1450f),
            DataPoint(2f, 1460f),
            DataPoint(3f, 1470f),
            DataPoint(4f, 1430f),
            DataPoint(5f, 1435f),
            DataPoint(6f, 1440f),
            DataPoint(7f, 1450f),
            DataPoint(8f, 1450f),
            DataPoint(9f, 1460f),
            DataPoint(10f, 1470f),
            DataPoint(11f, 1430f),
            DataPoint(12f, 1435f),
            DataPoint(13f, 1440f),
            DataPoint(14f, 1450f),
        )

        val dataPoints2 = listOf(
            DataPoint(1f, 25f),
            DataPoint(2f, 75f),
            DataPoint(3f, 100f),
            DataPoint(4f, 80f),
            DataPoint(5f, 75f),
            DataPoint(6f, 50f),
            DataPoint(7f, 80f),
        )

         */

        return listOf(weightVolume, pauseVolume)
        //return listOf(dataPoints1,dataPoints2)
    }
}