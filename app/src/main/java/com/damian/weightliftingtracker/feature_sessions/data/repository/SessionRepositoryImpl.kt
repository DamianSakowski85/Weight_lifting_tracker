package com.damian.weightliftingtracker.feature_sessions.data.repository

import com.damian.weightliftingtracker.feature_exercise.data.data_source.ExerciseDao
import com.damian.weightliftingtracker.feature_exercise_log.data.data_source.ExerciseLogDao
import com.damian.weightliftingtracker.feature_sessions.data.data_source.SessionDao
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl constructor(
    private val sessionDao: SessionDao,
    private val exerciseLogDao: ExerciseLogDao,
    private val exerciseDao: ExerciseDao
) : SessionRepository {
    override suspend fun getSessionById(id: Int): Session? =
        sessionDao.getSingleSessionById(id)

    override fun getSessions(planId: Int): Flow<List<Session>> =
        sessionDao.getSessions(planId)

    override suspend fun insertSession(session: Session) =
        sessionDao.insert(session)

    override suspend fun updateSession(session: Session) =
        sessionDao.update(session)


    override suspend fun deleteSessionById(sessionId: Int) =
        sessionDao.deleteById(sessionId)

    override suspend fun getExerciseIds(sessionId: Int): List<Int> {
        return exerciseDao.getExerciseIds(sessionId)
    }

    override suspend fun getLastSessionDateForExercise(exerciseId: Int): String? {
        return exerciseLogDao.getLastDate(exerciseId)
    }
}