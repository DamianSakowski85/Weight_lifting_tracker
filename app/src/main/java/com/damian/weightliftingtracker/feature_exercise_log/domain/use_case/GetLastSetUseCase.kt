package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository

class GetLastSetUseCase(
    private val exerciseLogRepository: ExerciseLogRepository
) {
    suspend operator fun invoke(exerciseId: Int): ExerciseLog? {
        return exerciseLogRepository.getLastSet(exerciseId)
    }
}