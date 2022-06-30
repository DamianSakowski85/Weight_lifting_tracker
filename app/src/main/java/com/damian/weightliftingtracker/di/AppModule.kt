package com.damian.weightliftingtracker.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.database.PlanDatabase
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
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
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesPlanDatabase(app: Application, callback: Callback): PlanDatabase {
        return Room.databaseBuilder(
            app,
            PlanDatabase::class.java,
            PlanDatabase.DATABASE_NAME,
        ).addCallback(callback).build()
    }

    private val MIGRATION_4_5 = object : Migration(4,5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //Drop column isn't supported by SQLite, so the data must manually be moved
            with(database) {
                //execSQL("CREATE TABLE Users_Backup(id INTEGER NOT NULL, exerciseId INTEGER NOT NULL, repsGoal INTEGER NOT NULL, weightGoal REAL NOT NULL, pauseGoal INTEGER NOT NULL, repsAchieved INTEGER NOT NULL, weightAchieved REAL NOT NULL, pauseAchieved INTEGER NOT NULL, date TEXT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (exerciseId) REFERENCES _exercise(id) ON DELETE CASCADE)")
                //execSQL("CREATE INDEX IF NOT EXISTS index__exercise_log_exerciseId ON Users_Backup(exerciseId)")
                //execSQL("INSERT INTO Users_Backup SELECT id, exerciseId, repsGoal, weightGoal, pauseGoal, repsAchieved, weightAchieved, pauseAchieved, date FROM _exercise_log")
                //execSQL("DROP TABLE _exercise_log")
                //execSQL("ALTER TABLE Users_Backup RENAME to _exercise_log")
            }
        }
    }
    @Provides
    @Singleton
    fun providesStringProvider(app: Application): StringProvider {
        return StringProvider(app)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(app: Application): PreferencesManager {
        return PreferencesManager(app)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

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
                    planName = "Sample Full Body Workout",
                    planDescription = "3 whole body workouts per week",
                    timestamp = System.currentTimeMillis()
                )
                database.planDao.insert(plan)

                val session = Session(
                    planId = 1,
                    sessionName = "Training A",
                    sessionDescription = "Desc of training A",
                )
                val sessionB = Session(
                    planId = 1,
                    sessionName = "Training B",
                    sessionDescription = "Desc of training A",
                )
                database.sessionDao.insert(session)
                database.sessionDao.insert(sessionB)

                val exerciseA = Exercise(
                    sessionId = 1,
                    exerciseName = "Deadlift",
                    exerciseDescription = "5 sets, 5 reps"
                )
                val exerciseB = Exercise(
                    sessionId = 1,
                    exerciseName = "Bench Press",
                    exerciseDescription = "5 sets, 5 reps"
                )
                val exerciseC = Exercise(
                    sessionId = 1,
                    exerciseName = "Barbell Row",
                    exerciseDescription = "5 sets, 5 reps"
                )
                database.exerciseDao.insert(exerciseA)
                database.exerciseDao.insert(exerciseB)
                database.exerciseDao.insert(exerciseC)

                val exerciseD = Exercise(
                    sessionId = 2,
                    exerciseName = "Squads",
                    exerciseDescription = "5 sets, 5 reps"
                )
                val exerciseE = Exercise(
                    sessionId = 2,
                    exerciseName = "Over Head Press",
                    exerciseDescription = "5 sets, 5 reps"
                )
                val exerciseF = Exercise(
                    sessionId = 2,
                    exerciseName = "Pull Ups",
                    exerciseDescription = "5 sets, 5 reps"
                )
                database.exerciseDao.insert(exerciseD)
                database.exerciseDao.insert(exerciseE)
                database.exerciseDao.insert(exerciseF)
            }
        }
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

