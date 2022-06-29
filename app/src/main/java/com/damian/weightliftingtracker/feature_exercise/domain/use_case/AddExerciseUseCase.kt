package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.model.InvalidExerciseException
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository

class AddExerciseUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    @Throws(InvalidExerciseException::class)
    suspend operator fun invoke(exercise: Exercise) {
        if (exercise.exerciseName.isBlank()) {
            throw InvalidExerciseException("The name of the exercise can't be empty.")
        }
        exerciseRepository.insertExercise(exercise = exercise)
    }
}