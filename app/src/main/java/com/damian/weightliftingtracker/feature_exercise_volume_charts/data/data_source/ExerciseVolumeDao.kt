package com.damian.weightliftingtracker.feature_exercise_volume_charts.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog

@Dao
interface ExerciseVolumeDao {

    @Query("Select date FROM _exercise_log WHERE exerciseId=:exerciseId GROUP BY date")
    suspend fun getDates(exerciseId: Int): List<String>

    @Query("SELECT * FROM _exercise_log WHERE exerciseId=:exerciseId AND date=:date")
    suspend fun getSession(exerciseId: Int, date: String): List<ExerciseLog>
}