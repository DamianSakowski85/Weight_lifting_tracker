package com.damian.weightliftingtracker.feature_sessions.domain.use_case

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSessionsUseCase(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(planId: Int): Flow<List<Session>> {

       return sessionRepository.getSessions(planId).map { sessions ->
           sessions.forEach {session ->
               val exerciseIds = sessionRepository.getExerciseIds(session.id)
               if (exerciseIds.isNotEmpty()){
                   val lastExerciseDate =
                       sessionRepository.getLastSessionDateForExercise(exerciseIds.first())
                   lastExerciseDate?.let { date ->
                       session.lastSessionDate = date
                   }
               }
           }
            sessions
        }
    }
}