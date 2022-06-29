package com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.repository.ExerciseVolumeRepository

class FakeExerciseVolumeRepo : ExerciseVolumeRepository {

    private val exerciseLogs = mutableListOf<ExerciseLog>()

    fun addExerciseLogForTest(exerciseLog: ExerciseLog) {
        exerciseLogs.add(exerciseLog)
    }

    override suspend fun getDates(exerciseId: Int): List<String> {
        val dates = mutableListOf<String>()
        exerciseLogs.forEach {
            if (!dates.contains(it.date)) {
                dates.add(it.date)
            }
        }
        return dates
    }

    override suspend fun getSession(exerciseId: Int, date: String): List<ExerciseLog> {
        val logsByDate = mutableListOf<ExerciseLog>()
        exerciseLogs.forEach {
            if (it.date == date) {
                logsByDate.add(it)
            }
        }

        return logsByDate
    }
}