package com.damian.weightliftingtracker.feature_sessions.domain.use_case

data class SessionUseCases(
    val getSessionsUseCase: GetSessionsUseCase,
    val addSessionUseCase: AddSessionUseCase,
    val updateSessionUseCase: UpdateSessionUseCase,
    val deleteSessionUseCase: DeleteSessionUseCase,
    val getSessionByIdUseCase: GetSessionByIdUseCase
)
