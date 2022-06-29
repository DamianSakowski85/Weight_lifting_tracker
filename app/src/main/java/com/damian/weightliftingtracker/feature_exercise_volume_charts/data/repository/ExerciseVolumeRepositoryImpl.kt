package com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.data_source.ExerciseVolumeDao
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.repository.ExerciseVolumeRepository

class ExerciseVolumeRepositoryImpl(
    private val exerciseVolumeDao: ExerciseVolumeDao
) : ExerciseVolumeRepository {
    override suspend fun getDates(exerciseId: Int): List<String> {
        return exerciseVolumeDao.getDates(exerciseId)
    }

    override suspend fun getSession(exerciseId: Int, date: String): List<ExerciseLog> {
        return exerciseVolumeDao.getSession(exerciseId, date)
    }
}