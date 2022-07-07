package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.InvalidSessionException
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpdateSessionUseCaseTest {

    private lateinit var updateSessionUseCase: UpdateSessionUseCase
    private lateinit var sessionRepository: SessionRepository
    private val sessionToInsert = Session(
        1,
        1,
        "name",
        "desc"
    )

    @Before
    fun setup() = runBlocking{
        sessionRepository =FakeSessionRepository()
        updateSessionUseCase = UpdateSessionUseCase(sessionRepository)

        sessionRepository.insertSession(sessionToInsert)
    }

    @Test(expected = InvalidSessionException::class)
    fun `Update session with blank name, exception test`() = runBlocking {
        val session = sessionRepository.getSessionById(1)

        val sessionToUpdate = session?.copy(
            sessionName = ""
        )

        if(sessionToUpdate != null) {
            updateSessionUseCase(sessionToUpdate)
        }
    }

    @Test
    fun `Update session, is updated`() = runBlocking {
        val session = sessionRepository.getSessionById(1)

        val sessionToUpdate = session?.copy(
            sessionName = "updated name"
        )

        if(sessionToUpdate != null) {
            updateSessionUseCase(sessionToUpdate)
        }

        Assert.assertEquals("updated name", sessionRepository.getSessionById(1)?.sessionName)
    }
}