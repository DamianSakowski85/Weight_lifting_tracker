package com.damian.weightliftingtracker.feature_sessions.presentation.utli

const val SESSION_SCREEN_ROUTE = "session_screen"
const val ADD_EDIT_SESSION_ROUTE = "add_edit_session_screen"

sealed class SessionScreens(val route: String) {
    object SessionScreen : SessionScreens(SESSION_SCREEN_ROUTE)
    object AddEditSessionScreen : SessionScreens(ADD_EDIT_SESSION_ROUTE)
}