package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.InvalidSessionException
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddSessionUseCaseTest {

    private lateinit var addSessionUseCase: AddSessionUseCase
    private lateinit var sessionRepository: SessionRepository

    @Before
    fun setup() {
        sessionRepository = FakeSessionRepository()
        addSessionUseCase = AddSessionUseCase(sessionRepository)
    }

    @Test(expected = InvalidSessionException::class)
    fun `Insert session with blank name, exception test`() = runBlocking {
        val sessionToInsert = Session(
            1,
            1,
            "",
            ""
        )
        addSessionUseCase(sessionToInsert)
    }

    @Test
    fun `Insert session, is inserted`() = runBlocking {
        val sessionToInsert = Session(
            1,
            1,
            "session a",
            "desc of session a"
        )
        addSessionUseCase(sessionToInsert)

        Assert.assertEquals(sessionToInsert, sessionRepository.getSessionById(1))
    }
}