package com.damian.weightliftingtracker.feature_exercise_log.domain.repository

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow

interface ExerciseLogRepository {

    suspend fun insert(exerciseLog: ExerciseLog)

    suspend fun delete(exerciseLog: ExerciseLog)

    fun getLogs(exerciseId: Int, date: String): Flow<List<ExerciseLog>>

    suspend fun getPreviousDate(exerciseId: Int, currentDate: String): String?

    suspend fun getLastSet(exerciseId: Int): ExerciseLog?
}