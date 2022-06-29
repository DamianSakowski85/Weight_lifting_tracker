package com.damian.weightliftingtracker.feature_exercise_log.domain.model

import androidx.annotation.NonNull
import androidx.room.*
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise

@Entity(
    indices = [Index("exerciseId")],
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class, parentColumns = arrayOf("id"),
            childColumns = arrayOf("exerciseId"), onDelete = ForeignKey.CASCADE
        )
    ],
    tableName = "_exercise_log"
)
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    //@NonNull
    val exerciseId: Int,
    val repsGoal: Int,
    val weightGoal: Double,
    val pauseGoal: Int,
    val repsAchieved: Int,
    val weightAchieved: Double,
    val pauseAchieved: Int,
    val date: String
) {
    @Ignore
    var setNumber: Int = 0
}

class InvalidExerciseLogException(message: String) : Exception(message)
