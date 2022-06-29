package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_history.domain.repository.ExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class LoadLogUseCase(
    private val exerciseHistoryRepository: ExerciseHistoryRepository
) {
    suspend operator fun invoke(exerciseId: Int, date: String?): Flow<List<ExerciseLog>> {
        date?.let {
            return exerciseHistoryRepository.getLog(exerciseId, it).map { logs ->
                var setNumber = 1
                logs.forEach {
                    it.setNumber = setNumber
                    setNumber++
                }
                logs
            }
        }

        val lastDate = exerciseHistoryRepository.getLastDate(exerciseId)
        lastDate?.let {
            return exerciseHistoryRepository.getLog(exerciseId, it).map { logs ->
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