package com.damian.weightliftingtracker.feature_exercise.domain.repository

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExerciseById(id: Int): Exercise?

    fun getExercises(sessionId: Int): Flow<List<Exercise>>

    suspend fun insertExercise(exercise: Exercise)

    suspend fun updateExercise(exercise: Exercise)

    suspend fun deleteExercise(exercise: Exercise)

    suspend fun clearExerciseData(exerciseId: Int)
}