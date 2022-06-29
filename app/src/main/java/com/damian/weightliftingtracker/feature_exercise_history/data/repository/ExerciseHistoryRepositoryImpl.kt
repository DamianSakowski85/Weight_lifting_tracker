package com.damian.weightliftingtracker.feature_exercise_history.data.repository

import com.damian.weightliftingtracker.feature_exercise_history.data.data_source.ExerciseHistoryDao
import com.damian.weightliftingtracker.feature_exercise_history.domain.repository.ExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow

class ExerciseHistoryRepositoryImpl(
    private val exerciseHistoryDao: ExerciseHistoryDao
) : ExerciseHistoryRepository {
    override suspend fun getDates(exerciseId: Int): List<String> {
        return exerciseHistoryDao.getDates(exerciseId)
    }

    override suspend fun getLastDate(exerciseId: Int): String? {
        return exerciseHistoryDao.getLastDate(exerciseId)
    }

    override fun getLog(exerciseId: Int, date: String): Flow<List<ExerciseLog>> {
        return exerciseHistoryDao.getLog(exerciseId, date)
    }
}