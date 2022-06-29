package com.damian.weightliftingtracker.feature_exercise.data.data_source

import androidx.room.*
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exercise: Exercise)

    @Update
    suspend fun update(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * FROM _exercise WHERE sessionId=:sessionId")
    fun getExercises(sessionId: Int): Flow<List<Exercise>>

    @Query("SELECT * FROM _exercise WHERE id=:id")
    suspend fun getSingleExerciseById(id: Int): Exercise?

    @Query("DELETE FROM _exercise_log WHERE exerciseId =:exerciseId")
    suspend fun clearExerciseData(exerciseId: Int)

    @Query("SELECT id FROM _exercise WHERE sessionId=:sessionId")
    suspend fun getExerciseIds(sessionId : Int) : List<Int>
}