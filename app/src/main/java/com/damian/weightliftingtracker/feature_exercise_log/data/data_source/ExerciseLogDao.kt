package com.damian.weightliftingtracker.feature_exercise_log.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.Flow
import java.net.IDN

@Dao
interface ExerciseLogDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(exerciseLog: ExerciseLog)

    @Delete
    suspend fun delete(exerciseLog: ExerciseLog)

    @Query("SELECT * FROM _exercise_log WHERE exerciseId=:exerciseId AND date=:date")
    fun getLogs(exerciseId: Int, date: String): Flow<List<ExerciseLog>>

    @Query(
        "SELECT date FROM _exercise_log " +
                "WHERE exerciseId=:exerciseId AND date !=:currentDate " +
                "ORDER BY id DESC LIMIT 1"
    )
    suspend fun getPreviousDate(exerciseId: Int, currentDate: String): String?

    @Query(
        "SELECT * FROM _exercise_log WHERE exerciseId=:exerciseId " +
                "ORDER BY id DESC LIMIT 1"
    )
    suspend fun getLastSet(exerciseId: Int): ExerciseLog?

    @Query(
        "SELECT date FROM _exercise_log WHERE exerciseId=:exerciseId ORDER BY id DESC"
    )
    suspend fun getLastDate(exerciseId: Int) : String?
}