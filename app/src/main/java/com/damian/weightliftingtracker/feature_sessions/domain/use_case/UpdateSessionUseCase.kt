package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.domain.model.InvalidSessionException
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository

class UpdateSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    @Throws(InvalidSessionException::class)
    suspend operator fun invoke(session: Session) {
        if (session.sessionName.isBlank()) {
            throw InvalidSessionException("The name of the plan can't be empty.")
        }
        sessionRepository.updateSession(session = session)
    }
}