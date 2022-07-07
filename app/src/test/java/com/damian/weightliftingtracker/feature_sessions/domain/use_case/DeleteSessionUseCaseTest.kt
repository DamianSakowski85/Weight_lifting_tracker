package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteSessionUseCaseTest {

    private lateinit var deleteSessionUseCase: DeleteSessionUseCase
    private lateinit var sessionRepository: SessionRepository

    val sessionToDelete = Session(
        1,
        1,
        "name",
        "desc"
    )

    @Before
    fun setup() = runBlocking {
        sessionRepository = FakeSessionRepository()
        deleteSessionUseCase = DeleteSessionUseCase(sessionRepository)

        sessionRepository.insertSession(sessionToDelete)
    }

    @Test
    fun `Delete session, is deleted`() = runBlocking {
        deleteSessionUseCase(sessionToDelete)

        assertEquals(null,sessionRepository.getSessionById(1))
    }
}