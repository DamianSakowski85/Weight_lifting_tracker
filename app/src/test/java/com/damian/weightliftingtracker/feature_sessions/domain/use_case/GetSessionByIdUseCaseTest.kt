package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSessionByIdUseCaseTest {

    private lateinit var getSessionByIdUseCase: GetSessionByIdUseCase
    private lateinit var testSessionRepository: FakeSessionRepository

    private val sessionToInsert = Session(
        1,
        1,
        "name",
        "desc"
    )

    @Before
    fun setup() = runBlocking {
        testSessionRepository = FakeSessionRepository()
        getSessionByIdUseCase = GetSessionByIdUseCase(testSessionRepository)

        testSessionRepository.insertSession(sessionToInsert)
    }

    @Test
    fun `Get session by id`() = runBlocking {
        val insertedSession = getSessionByIdUseCase(1)

        Assert.assertEquals(insertedSession,sessionToInsert)
    }
}