package com.damian.weightliftingtracker.feature_exercise_history.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseHistoryDao {
    @Query("Select date FROM _exercise_log WHERE exerciseId=:exerciseId GROUP BY date")
    suspend fun getDates(exerciseId: Int): List<String>

    @Query("Select date From _exercise_log Where exerciseId=:exerciseId Order By id Desc Limit 1")
    suspend fun getLastDate(exerciseId: Int): String?

    @Query("Select * From _exercise_log Where exerciseId=:exerciseId And date=:date")
    fun getLog(exerciseId: Int, date: String): Flow<List<ExerciseLog>>
}