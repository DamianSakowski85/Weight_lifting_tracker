package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSessionByIdUseCaseTest {

    private lateinit var getSessionByIdUseCase: GetSessionByIdUseCase
    private lateinit var sessionRepository: SessionRepository

    private val sessionToInsert = Session(
        1,
        1,
        "name",
        "desc"
    )

    @Before
    fun setup() = runBlocking {
        sessionRepository = FakeSessionRepository()
        getSessionByIdUseCase = GetSessionByIdUseCase(sessionRepository)

        sessionRepository.insertSession(sessionToInsert)
    }

    @Test
    fun `Get session by id`() = runBlocking {
        val insertedSession = getSessionByIdUseCase(1)

        Assert.assertEquals(insertedSession,sessionToInsert)
    }
}