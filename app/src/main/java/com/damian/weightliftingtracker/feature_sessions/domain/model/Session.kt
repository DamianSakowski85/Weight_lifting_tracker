package com.damian.weightliftingtracker.feature_sessions.domain.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan

@Entity(
    indices = [Index("planId")],
    foreignKeys = [
        ForeignKey(
            entity = Plan::class, parentColumns = arrayOf("id"),
            childColumns = arrayOf("planId"), onDelete = CASCADE
        )
    ],
    tableName = "_session"
)
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val planId: Int,
    val sessionName: String,
    val sessionDescription: String
){
    @Ignore
    var lastSessionDate : String = "none"
}

class InvalidSessionException(message: String) : Exception(message)
