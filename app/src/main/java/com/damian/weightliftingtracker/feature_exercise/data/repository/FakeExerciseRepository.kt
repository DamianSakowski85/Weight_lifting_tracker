package com.damian.weightliftingtracker.feature_exercise.data.repository

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeExerciseRepository : ExerciseRepository {

    private val exercises = mutableListOf<Exercise>()
    private val exerciseLogs = mutableListOf<ExerciseLog>()

    fun addExerciseLogsForTest(exerciseLog: ExerciseLog) {
        exerciseLogs.add(exerciseLog)
    }

    fun getExerciseLogsData(): List<ExerciseLog> {
        return exerciseLogs
    }

    override suspend fun getExerciseById(id: Int): Exercise? {
        return exercises.find { it.id == id }
    }

    override fun getExercises(sessionId: Int): Flow<List<Exercise>> {
        return flowOf(exercises)
    }

    override suspend fun insertExercise(exercise: Exercise) {
        exercises.add(exercise)
    }

    override suspend fun updateExercise(exercise: Exercise) {
        val exerciseToRemove = exercises.find { it.id == exercise.id }
        exercises.remove(exerciseToRemove)

        exercises.add(exercise)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        val exerciseToRemove = exercises.find { it.id == exercise.id }
        exercises.remove(exerciseToRemove)
    }

    override suspend fun clearExerciseData(exerciseId: Int) {
        exerciseLogs.clear()
    }
}