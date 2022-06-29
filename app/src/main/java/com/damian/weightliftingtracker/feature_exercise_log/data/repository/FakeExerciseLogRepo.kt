package com.damian.weightliftingtracker.feature_exercise_log.data.repository

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeExerciseLogRepo : ExerciseLogRepository {

    private val exerciseLogs = mutableListOf<ExerciseLog>()

    override suspend fun insert(exerciseLog: ExerciseLog) {
        exerciseLogs.add(exerciseLog)
    }

    override suspend fun delete(exerciseLog: ExerciseLog) {
        exerciseLogs.remove(exerciseLog)
    }

    override fun getLogs(exerciseId: Int, date: String): Flow<List<ExerciseLog>> {
        return flowOf(exerciseLogs).map { logs ->
            val logsByDateAndExerciseId = mutableListOf<ExerciseLog>()
            logs.forEach {
                if (it.exerciseId == exerciseId && it.date == date) {
                    logsByDateAndExerciseId.add(it)
                }
            }
            logsByDateAndExerciseId
        }
        /*
        return exerciseLogs.map {
            val logsByDateAndExerciseId = mutableListOf<ExerciseLog>()
            exerciseLogs.forEach {
                if (it.exerciseId == exerciseId && it.date == date){
                    logsByDateAndExerciseId.add(it)
                }
            }
            emit(logsByDateAndExerciseId)
        }

         */
    }

    override suspend fun getPreviousDate(exerciseId: Int, currentDate: String): String? {
        val dates = mutableListOf<String>()
        exerciseLogs.forEach {
            if (it.date != currentDate) {
                if (!dates.contains(it.date)) {
                    dates.add(it.date)
                }
            }
        }
        return if (dates.size != 0) {
            dates.sortDescending()
            return dates.last()
        } else {
            null
        }
    }

    override suspend fun getLastSet(exerciseId: Int): ExerciseLog? {
        return if (exerciseLogs.size != 0) {
            exerciseLogs.last()
        } else {
            null
        }
    }
}