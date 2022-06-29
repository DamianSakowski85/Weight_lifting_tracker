package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository

class ClearExerciseDataUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(exerciseId: Int) {
        exerciseRepository.clearExerciseData(exerciseId)
    }
}