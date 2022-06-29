package com.damian.weightliftingtracker.feature_sessions.data.repository

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSessionRepository : SessionRepository {

    private val sessions = mutableListOf<Session>()

    override suspend fun getSessionById(id: Int): Session? {
        return sessions.find { it.id == id }
    }

    override fun getSessions(planId: Int): Flow<List<Session>> {
        return flowOf(sessions)
    }

    override suspend fun insertSession(session: Session) {
        sessions.add(session)
    }

    override suspend fun updateSession(session: Session) {
        val sessionToRemove = sessions.find { it.id == session.id }
        sessions.remove(sessionToRemove)

        sessions.add(session)
    }

    override suspend fun deleteSessionById(sessionId: Int) {
        val sessionToRemove = sessions.find { it.id == sessionId }
        sessions.remove(sessionToRemove)
    }

    override suspend fun getExerciseIds(sessionId: Int): List<Int> {
        return emptyList()
    }

    override suspend fun getLastSessionDateForExercise(exerciseId: Int): String {
        return ""
    }
}