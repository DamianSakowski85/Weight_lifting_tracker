package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class PreviousExerciseLogsUseCase(
    private val exerciseLogsRepository: ExerciseLogRepository
) {
    suspend operator fun invoke(exerciseId: Int, currentDate: String): Flow<List<ExerciseLog>> {
        val previousDate = exerciseLogsRepository.getPreviousDate(exerciseId, currentDate)
        previousDate?.let {
            return exerciseLogsRepository.getLogs(exerciseId, previousDate).map { logs ->
                var setNumber = 1
                logs.forEach {
                    it.setNumber = setNumber
                    setNumber++
                }
                logs
            }
        }
        return emptyFlow()
    }
}