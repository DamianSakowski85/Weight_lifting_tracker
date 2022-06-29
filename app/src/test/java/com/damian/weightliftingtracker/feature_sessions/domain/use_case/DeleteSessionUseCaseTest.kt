package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteSessionUseCaseTest {

    private lateinit var deleteSessionUseCase: DeleteSessionUseCase
    private lateinit var testSessionRepository: FakeSessionRepository

    val sessionToDelete = Session(
        1,
        1,
        "name",
        "desc"
    )

    @Before
    fun setup() = runBlocking {
        testSessionRepository = FakeSessionRepository()
        deleteSessionUseCase = DeleteSessionUseCase(testSessionRepository)

        testSessionRepository.insertSession(sessionToDelete)
    }

    @Test
    fun `Delete session, is deleted`() = runBlocking {
        deleteSessionUseCase(sessionToDelete)

        assertEquals(null,testSessionRepository.getSessionById(1))
    }
}