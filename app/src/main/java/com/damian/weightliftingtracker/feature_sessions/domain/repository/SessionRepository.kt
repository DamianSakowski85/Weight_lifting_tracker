package com.damian.weightliftingtracker.feature_sessions.domain.repository

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun getSessionById(id: Int): Session?

    fun getSessions(planId: Int): Flow<List<Session>>

    suspend fun insertSession(session: Session)

    suspend fun updateSession(session: Session)

    suspend fun deleteSessionById(sessionId: Int)

    suspend fun getExerciseIds(sessionId: Int) : List<Int>

    suspend fun getLastSessionDateForExercise(exerciseId : Int) : String?
}