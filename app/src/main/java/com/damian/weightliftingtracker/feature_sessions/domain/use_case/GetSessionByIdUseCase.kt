package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository

class GetSessionByIdUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(sessionId: Int): Session? {
        return sessionRepository.getSessionById(sessionId)
    }
}