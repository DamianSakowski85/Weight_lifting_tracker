package com.damian.weightliftingtracker.feature_sessions.presentation.sessions

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session

data class SessionsState(
    val planId: Int = -1,
    val planName: String = "",
    val sessions: List<Session> = emptyList(),
    val selectedSession: Session? = null,
    val isDeleteDialogVisible: Boolean = false,
    val isEmptyListLabelVisible: Boolean = false
)
