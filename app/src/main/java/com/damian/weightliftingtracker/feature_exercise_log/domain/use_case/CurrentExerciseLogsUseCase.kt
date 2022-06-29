package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentExerciseLogsUseCase(
    private val exerciseLogsRepository: ExerciseLogRepository
) {
    operator fun invoke(exerciseId: Int, date: String): Flow<List<ExerciseLog>> {
        return exerciseLogsRepository.getLogs(exerciseId, date).map { logs ->
            var setNumber = 1
            logs.forEach {
                it.setNumber = setNumber
                setNumber++
            }
            logs
        }
    }
}