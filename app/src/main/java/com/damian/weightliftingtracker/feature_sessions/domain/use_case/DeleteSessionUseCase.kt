package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository

class DeleteSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(session: Session) {
        sessionRepository.deleteSessionById(sessionId = session.id)
    }
}