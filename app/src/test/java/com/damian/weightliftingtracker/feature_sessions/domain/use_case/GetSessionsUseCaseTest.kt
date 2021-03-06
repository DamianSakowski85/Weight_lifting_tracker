package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSessionsUseCaseTest {

    private lateinit var getSessionsUseCase: GetSessionsUseCase
    private lateinit var sessionRpository : SessionRepository

    @Before
    fun setup(){
        sessionRpository = FakeSessionRepository()
        getSessionsUseCase = GetSessionsUseCase(sessionRpository)

        val sessionToInsert = mutableListOf<Session>()
        (1..5).forEachIndexed { index, c ->
            sessionToInsert.add(
                Session(
                    id = index,
                    planId = 1,
                    sessionName = c.toString(),
                    sessionDescription = "$c desc",
                )
            )
        }

        runBlocking {
            sessionToInsert.forEach {
                sessionRpository.insertSession(it)
            }
        }
    }

    @Test
    fun `Get all sessions, 5 items`() = runBlocking{
        val sessions = getSessionsUseCase(1).first()

        Assert.assertEquals(5,sessions.size)
    }
}