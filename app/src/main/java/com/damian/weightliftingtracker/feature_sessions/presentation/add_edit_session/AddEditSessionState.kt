package com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session

import com.damian.weightliftingtracker.feature_sessions.domain.model.Session

data class AddEditSessionState(
    val sessionToEdit: Session? = null,
    val planId: Int? = null,
    val name: String = "",
    val description: String = ""
)
