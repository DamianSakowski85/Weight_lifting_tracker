package com.damian.weightliftingtracker.feature_sessions.presentation.sessions

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session

sealed class SessionsEvent {
    object OnAdd : SessionsEvent()
    data class OnSessionClick(val session: Session) : SessionsEvent()
    object OnConfirmDeletion : SessionsEvent()
    object OnDismissDialog : SessionsEvent()
    object OnBackPressed : SessionsEvent()
    data class OnSessionMenuClick(val selectedSession: Session) : SessionsEvent()
    object OnDelete : SessionsEvent()
    object OnEdit : SessionsEvent()
}