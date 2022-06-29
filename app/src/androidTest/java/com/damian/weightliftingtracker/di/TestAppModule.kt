package com.damian.weightliftingtracker.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.database.PlanDatabase
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_plans.data.data_source.PreferencesManager
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    class Callback @Inject constructor(
        private val database: Provider<PlanDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {
                pop(database.get())
            }
        }

        private suspend fun pop(database: PlanDatabase?) {
            database?.let {
                val plan = Plan(
                    planName = "Fbw",
                    planDescription = "Fbw desc",
                    timestamp = 1
                )
                val plan2 = Plan(
                    planName = "Push Pull Legs 2",
                    planDescription = "push pull legs desc",
                    timestamp = 2
                )
                database.planDao.insert(plan)
                database.planDao.insert(plan2)


                val session = Session(
                    planId = 2,
                    sessionName = "Training A",
                    sessionDescription = "Desc of training A"
                )
                database.sessionDao.insert(session)

                val exercise = Exercise(
                    sessionId = 1,
                    exerciseName = "Bench Press",
                    exerciseDescription = "desc of bench press"
                )
                database.exerciseDao.insert(exercise)

                var days = 5
                (1..5).forEachIndexed { index, c ->
                    database.exerciseLogDao.insert(
                        ExerciseLog(
                            id = index,
                            exerciseId = 1,
                            8,
                            55.0,
                            180,
                            5 + index,
                            55.0,
                            180,
                            LocalDate.now().minusDays(days.toLong()).toString()
                        )
                    )
                    days --
                }

            }
        }
    }

    @Provides
    @Singleton
    fun providesPlanDatabase(app: Application,callback: Callback): PlanDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            PlanDatabase::class.java
        ).fallbackToDestructiveMigration().addCallback(callback).build()
    }

    @Provides
    @Singleton
    fun providesStringProvider(app: Application) : StringProvider{
        return StringProvider(app)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(app: Application) : PreferencesManager {
        return PreferencesManager(app)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope