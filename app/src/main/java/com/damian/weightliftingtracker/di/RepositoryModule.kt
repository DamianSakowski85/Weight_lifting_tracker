package com.damian.weightliftingtracker.di

import com.damian.weightliftingtracker.core.database.PlanDatabase
import com.damian.weightliftingtracker.feature_exercise.data.repository.ExerciseRepositoryImpl
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import com.damian.weightliftingtracker.feature_exercise_history.data.repository.ExerciseHistoryRepositoryImpl
import com.damian.weightliftingtracker.feature_exercise_history.domain.repository.ExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_log.data.repository.ExerciseLogRepositoryImpl
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository.ExerciseVolumeRepositoryImpl
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.repository.ExerciseVolumeRepository
import com.damian.weightliftingtracker.feature_plans.data.data_source.PreferencesManager
import com.damian.weightliftingtracker.feature_plans.data.repository.PlanRepositoryImpl
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_sessions.data.repository.SessionRepositoryImpl
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesPlanRepository(
        db: PlanDatabase,
        preferencesManager: PreferencesManager
    ): PlanRepository {
        return PlanRepositoryImpl(db.planDao, preferencesManager)
    }

    @Provides
    @Singleton
    fun providesSessionRepository(db: PlanDatabase): SessionRepository {
        return SessionRepositoryImpl(db.sessionDao,db.exerciseLogDao,db.exerciseDao)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(db: PlanDatabase): ExerciseRepository {
        return ExerciseRepositoryImpl(db.exerciseDao)
    }

    @Provides
    @Singleton
    fun provideExerciseLogRepository(db: PlanDatabase): ExerciseLogRepository {
        return ExerciseLogRepositoryImpl(db.exerciseLogDao)
    }

    @Provides
    @Singleton
    fun provideExerciseVolumeRepository(db: PlanDatabase): ExerciseVolumeRepository {
        return ExerciseVolumeRepositoryImpl(db.exerciseVolumeDao)
    }

    @Provides
    @Singleton
    fun provideExerciseHistoryRepository(db: PlanDatabase): ExerciseHistoryRepository {
        return ExerciseHistoryRepositoryImpl(db.exerciseHistoryDao)
    }
}