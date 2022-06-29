package com.damian.weightliftingtracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.damian.weightliftingtracker.feature_exercise.data.data_source.ExerciseDao
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise_history.data.data_source.ExerciseHistoryDao
import com.damian.weightliftingtracker.feature_exercise_log.data.data_source.ExerciseLogDao
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.data_source.ExerciseVolumeDao
import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanDao
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_sessions.data.data_source.SessionDao
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session

@Database(
    entities = [Plan::class, Session::class, Exercise::class, ExerciseLog::class],
    version = 1
)
abstract class PlanDatabase : RoomDatabase() {
    abstract val planDao: PlanDao
    abstract val sessionDao: SessionDao
    abstract val exerciseDao: ExerciseDao
    abstract val exerciseLogDao: ExerciseLogDao
    abstract val exerciseVolumeDao: ExerciseVolumeDao
    abstract val exerciseHistoryDao: ExerciseHistoryDao

    companion object {
        const val DATABASE_NAME = "plans_db"
    }
}