package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.InvalidExerciseLogException
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository

class AddExerciseLogUseCase(
    private val exerciseLogRepository: ExerciseLogRepository
) {
    @Throws(InvalidExerciseLogException::class)
    suspend operator fun invoke(
        exerciseId: Int,
        weightGoal: String,
        repsGoal: String,
        pauseGoal: String,
        weightAchieved: String,
        repsAchieved: String,
        pauseAchieved: String,
        date: String
    ): ExerciseLog {
        when {
            weightGoal.isBlank() -> {
                throw InvalidExerciseLogException("Weight can't be empty.")
            }
            repsGoal.isBlank() -> {
                throw InvalidExerciseLogException("Reps can't be empty.")
            }
            pauseGoal.isBlank() -> {
                throw InvalidExerciseLogException("Pause can't be empty.")
            }
        }
        try {
            val weightGoalDouble = weightGoal.toDouble()
            val repsGoalInt = repsGoal.toInt()
            val pauseGoalInt = pauseGoal.toInt()

            val weightAchievedDouble: Double = if (weightAchieved.isNotBlank()) {
                weightAchieved.toDouble()
            } else {
                weightGoalDouble
            }

            val repsAchievedInt: Int = if (repsAchieved.isNotBlank()) {
                repsAchieved.toInt()
            } else {
                repsGoalInt
            }

            val pauseAchievedInt: Int = if (pauseAchieved.isNotBlank()) {
                pauseAchieved.toInt()
            } else {
                pauseGoalInt
            }

            val exerciseLog = ExerciseLog(
                exerciseId = exerciseId,
                weightGoal = weightGoalDouble,
                repsGoal = repsGoalInt,
                pauseGoal = pauseGoalInt,
                weightAchieved = weightAchievedDouble,
                repsAchieved = repsAchievedInt,
                pauseAchieved = pauseAchievedInt,
                date = date
            )
            exerciseLogRepository.insert(exerciseLog)

            return exerciseLog
        } catch (exception: Exception) {
            throw InvalidExerciseLogException(exception.message.toString())
        }
    }
}