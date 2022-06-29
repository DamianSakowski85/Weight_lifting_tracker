package com.damian.weightliftingtracker.feature_exercise_history.domain.repository

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow

interface ExerciseHistoryRepository {
    suspend fun getDates(exerciseId: Int): List<String>
    suspend fun getLastDate(exerciseId: Int): String?
    fun getLog(exerciseId: Int, date: String): Flow<List<ExerciseLog>>
}