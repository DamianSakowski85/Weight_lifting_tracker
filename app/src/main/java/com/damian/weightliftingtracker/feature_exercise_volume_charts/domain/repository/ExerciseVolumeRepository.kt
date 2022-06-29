package com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.repository

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog

interface ExerciseVolumeRepository {
    suspend fun getDates(exerciseId: Int): List<String>
    suspend fun getSession(exerciseId: Int, date: String): List<ExerciseLog>
}