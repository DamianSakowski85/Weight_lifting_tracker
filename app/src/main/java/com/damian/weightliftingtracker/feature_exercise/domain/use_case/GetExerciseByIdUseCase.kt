package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository

class GetExerciseByIdUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(exerciseId: Int): Exercise? {
        return exerciseRepository.getExerciseById(exerciseId)
    }
}