package com.damian.weightliftingtracker.feature_plans.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "_plan")
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val planName: String,
    val planDescription: String,
    val timestamp: Long
) {
    val getTimestampFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timestamp)
}

class InvalidPlanException(message: String) : Exception(message)