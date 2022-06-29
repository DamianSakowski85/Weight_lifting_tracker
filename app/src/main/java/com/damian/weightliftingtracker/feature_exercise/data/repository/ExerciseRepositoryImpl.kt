package com.damian.weightliftingtracker.feature_exercise.data.repository

import com.damian.weightliftingtracker.feature_exercise.data.data_source.ExerciseDao
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class ExerciseRepositoryImpl constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {

    override suspend fun getExerciseById(id: Int): Exercise? {
        return exerciseDao.getSingleExerciseById(id)
    }

    override fun getExercises(sessionId: Int): Flow<List<Exercise>> {
        return exerciseDao.getExercises(sessionId)
    }

    override suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }

    override suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }

    override suspend fun clearExerciseData(exerciseId: Int) {
        exerciseDao.clearExerciseData(exerciseId)
    }
}