package com.damian.weightliftingtracker.feature_exercise_history.data.repository

import com.damian.weightliftingtracker.feature_exercise_history.domain.repository.ExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeExerciseHistoryRepository : ExerciseHistoryRepository {

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

    override suspend fun getLastDate(exerciseId: Int): String? {
        val dates = mutableListOf<String>()
        exerciseLogs.forEach {
            if (!dates.contains(it.date)) {
                dates.add(it.date)
            }
        }
        if (dates.size != 0) {
            return dates[dates.size - 1]
        }
        return null
    }

    override fun getLog(exerciseId: Int, date: String): Flow<List<ExerciseLog>> {
        val logsByDate = mutableListOf<ExerciseLog>()
        exerciseLogs.forEach {
            if (it.date == date) {
                logsByDate.add(it)
            }
        }

        return flow { emit(logsByDate) }
    }
}