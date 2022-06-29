package com.damian.weightliftingtracker.feature_exercise_log.data.repository

import com.damian.weightliftingtracker.feature_exercise_log.data.data_source.ExerciseLogDao
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import kotlinx.coroutines.flow.Flow

class ExerciseLogRepositoryImpl(
    private val exerciseLogDao: ExerciseLogDao
) : ExerciseLogRepository {
    override suspend fun insert(exerciseLog: ExerciseLog) {
        exerciseLogDao.insert(exerciseLog)
    }

    override suspend fun delete(exerciseLog: ExerciseLog) {
        exerciseLogDao.delete(exerciseLog)
    }

    override fun getLogs(exerciseId: Int, date: String): Flow<List<ExerciseLog>> {
        return exerciseLogDao.getLogs(exerciseId, date)
    }

    override suspend fun getPreviousDate(exerciseId: Int, currentDate: String): String? {
        return exerciseLogDao.getPreviousDate(exerciseId, currentDate)
    }

    override suspend fun getLastSet(exerciseId: Int): ExerciseLog? {
        return exerciseLogDao.getLastSet(exerciseId)
    }
}