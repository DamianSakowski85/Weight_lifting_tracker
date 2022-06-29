package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class GetExercisesUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    operator fun invoke(sessionId: Int): Flow<List<Exercise>> {
        return exerciseRepository.getExercises(sessionId)
    }
}