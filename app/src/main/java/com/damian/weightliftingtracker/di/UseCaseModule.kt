package com.damian.weightliftingtracker.di

import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.use_case.*
import com.damian.weightliftingtracker.feature_exercise_history.domain.repository.ExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.ExerciseHistoryUseCases
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.GetCalendarConstrainUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.LoadLogUseCase
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.*
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.repository.ExerciseVolumeRepository
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case.GetVolumeListUseCase
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.use_case.*
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesPlanUseCases(planRepository: PlanRepository): PlanUseCases {
        return PlanUseCases(
            getPlanUseCases = GetPlansUseCase(planRepository),
            deletePlanUseCase = DeletePlanUseCase(planRepository),
            addPlanUseCase = AddPlanUseCase(planRepository),
            updatePlanUseCase = UpdatePlanUseCase(planRepository),
            getPlanByIdUseCase = GetPlanByIdUseCase(planRepository),
            getSortOrderFromPrefUseCase = GetSortOrderFromPrefUseCase(planRepository),
            updateSortOrderInPrefUseCase = UpdateSortOrderInPrefUseCase(planRepository)
        )
    }

    @Provides
    @Singleton
    fun providesSessionUseCases(sessionRepository: SessionRepository): SessionUseCases {
        return SessionUseCases(
            getSessionsUseCase = GetSessionsUseCase(sessionRepository),
            addSessionUseCase = AddSessionUseCase(sessionRepository),
            deleteSessionUseCase = DeleteSessionUseCase(sessionRepository),
            getSessionByIdUseCase = GetSessionByIdUseCase(sessionRepository),
            updateSessionUseCase = UpdateSessionUseCase(sessionRepository)
        )
    }

    @Provides
    @Singleton
    fun providesExerciseUseCases(exerciseRepository: ExerciseRepository): ExerciseUseCases {
        return ExerciseUseCases(
            addExerciseUseCase = AddExerciseUseCase(exerciseRepository),
            deleteExerciseUseCase = DeleteExerciseUseCase(exerciseRepository),
            getExercisesUseCase = GetExercisesUseCase(exerciseRepository),
            getExerciseByIdUseCase = GetExerciseByIdUseCase(exerciseRepository),
            updateExerciseUseCase = UpdateExerciseUseCase(exerciseRepository),
            clearExerciseDataUseCase = ClearExerciseDataUseCase(exerciseRepository)
        )
    }

    @Provides
    @Singleton
    fun provideExerciseLogUseCases(exerciseLogRepository: ExerciseLogRepository)
            : ExerciseLogUseCases {
        return ExerciseLogUseCases(
            addExerciseLogUseCase = AddExerciseLogUseCase(exerciseLogRepository),
            currentExerciseLogsUseCase = CurrentExerciseLogsUseCase(exerciseLogRepository),
            previousExerciseLogsUseCase = PreviousExerciseLogsUseCase(exerciseLogRepository),
            deleteSelectedExerciseLogUseCase = DeleteSelectedExerciseLogUseCase(
                exerciseLogRepository
            ),
            increaseWeightValueUseCase = IncreaseWeightValueUseCase(),
            decreaseWeightValueUseCase = DecreaseWeightValueUseCase(),
            increaseRepsValueUseCase = IncreaseRepsValueUseCase(),
            decreaseRepsValueUseCase = DecreaseRepsValueUseCase(),
            increasePauseValueUseCase = IncreasePauseValueUseCase(),
            decreasePauseValueUseCase = DecreasePauseValueUseCase(),
            calculateVolumeUseCase = CalculateVolumeUseCase(),
            getLastSetUseCase = GetLastSetUseCase(exerciseLogRepository)
        )
    }

    @Provides
    @Singleton
    fun provideGetVolumeListUseCase(exerciseVolumeRepository: ExerciseVolumeRepository)
            : GetVolumeListUseCase {
        return GetVolumeListUseCase(exerciseVolumeRepository)
    }

    @Provides
    @Singleton
    fun provideExerciseHistoryUseCases(
        exerciseHistoryRepository: ExerciseHistoryRepository
    ): ExerciseHistoryUseCases {
        return ExerciseHistoryUseCases(
            loadLogUseCase = LoadLogUseCase(exerciseHistoryRepository),
            calculateVolumeUseCase = com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.CalculateVolumeUseCase(),
            getCalendarConstrainUseCase = GetCalendarConstrainUseCase(exerciseHistoryRepository)
        )
    }
}