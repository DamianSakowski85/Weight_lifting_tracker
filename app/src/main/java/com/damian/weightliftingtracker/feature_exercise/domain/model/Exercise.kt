package com.damian.weightliftingtracker.feature_exercise.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session

@Entity(
    indices = [Index("sessionId")],
    foreignKeys = [
        ForeignKey(
            entity = Session::class, parentColumns = arrayOf("id"),
            childColumns = arrayOf("sessionId"), onDelete = ForeignKey.CASCADE
        )
    ],
    tableName = "_exercise"
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sessionId: Int,
    val exerciseName: String,
    val exerciseDescription: String
)

class InvalidExerciseException(message: String) : Exception(message)
