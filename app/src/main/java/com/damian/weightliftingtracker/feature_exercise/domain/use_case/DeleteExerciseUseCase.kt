package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository

class DeleteExerciseUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise) {
        exerciseRepository.deleteExercise(exercise)
    }
}