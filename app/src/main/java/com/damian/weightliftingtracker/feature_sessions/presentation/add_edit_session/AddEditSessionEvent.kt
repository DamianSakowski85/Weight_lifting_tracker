package com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session

sealed class AddEditSessionEvent {
    data class OnEnterName(val value: String) : AddEditSessionEvent()
    data class OnEnterDescription(val value: String) : AddEditSessionEvent()
    object OnSaveSession : AddEditSessionEvent()
    object OnCloseClick : AddEditSessionEvent()
}
