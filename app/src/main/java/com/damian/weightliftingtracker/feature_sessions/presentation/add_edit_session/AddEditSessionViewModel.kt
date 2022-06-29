package com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_sessions.domain.model.InvalidSessionException
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.SessionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_SESSION_NAME = "entered_session_name"
const val STATE_KEY_SESSION_DESCRIPTION = "entered_session_description"
const val STATE_KEY_PLAN_ID = "planId"
const val STATE_KEY_SESSION_ID = "sessionId"

@HiltViewModel
class AddEditSessionViewModel @Inject constructor(
    private val sessionUseCases: SessionUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val stringProvider: StringProvider
) : ViewModel() {

    private var _currentSession: Session? = null
    private val _addEditSessionState = mutableStateOf(
        AddEditSessionState()
    )
    val uiState: State<AddEditSessionState> = _addEditSessionState

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(STATE_KEY_SESSION_ID)?.let {
                _currentSession = sessionUseCases.getSessionByIdUseCase(it)
            }
            _addEditSessionState.value = uiState.value.copy(
                name = savedStateHandle.get<String>(STATE_KEY_SESSION_NAME)
                    ?: _currentSession?.sessionName ?: "",

                description = savedStateHandle.get<String>(STATE_KEY_SESSION_DESCRIPTION)
                    ?: _currentSession?.sessionDescription ?: "",

                planId = savedStateHandle.get<Int>(STATE_KEY_PLAN_ID),
                sessionToEdit = _currentSession
            )
        }
    }

    private val _eventFlow = MutableSharedFlow<AddEditSessionUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddEditSessionEvent) {
        when (event) {
            is AddEditSessionEvent.OnEnterDescription -> {
                updateDescriptionState(event.value)
            }
            is AddEditSessionEvent.OnEnterName -> {
                updateNameState(event.value)
            }
            is AddEditSessionEvent.OnCloseClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(AddEditSessionUiEvent.NavBack)
                }
            }
            is AddEditSessionEvent.OnSaveSession -> {
                saveSession()
            }
        }
    }

    private fun saveSession() {
        viewModelScope.launch {
            try {
                _addEditSessionState.value.sessionToEdit?.let {
                    val sessionToUpdate = it.copy(
                        sessionName = _addEditSessionState.value.name,
                        sessionDescription = _addEditSessionState.value.description
                    )
                    sessionUseCases.updateSessionUseCase(sessionToUpdate)
                    _eventFlow.emit(
                        AddEditSessionUiEvent.ShowMessage(
                            stringProvider.getString(R.string.session_updated)
                        )
                    )
                    _eventFlow.emit(AddEditSessionUiEvent.NavBack)
                } ?: run {
                    _addEditSessionState.value.planId?.let {
                        sessionUseCases.addSessionUseCase(
                            Session(
                                planId = it,
                                sessionName = _addEditSessionState.value.name,
                                sessionDescription = _addEditSessionState.value.description
                            )
                        )
                    }
                    _eventFlow.emit(
                        AddEditSessionUiEvent.ShowMessage(
                            stringProvider.getString(R.string.session_added)
                        )
                    )
                    _eventFlow.emit(AddEditSessionUiEvent.NavBack)
                }
            } catch (exception: InvalidSessionException) {
                _eventFlow.emit(
                    AddEditSessionUiEvent.ShowMessage(
                        message = exception.message
                            ?: stringProvider.getString(R.string.could_not_save_session)
                    )
                )
            }
        }
    }

    private fun updateNameState(name: String) {
        _addEditSessionState.value = uiState.value.copy(
            name = name
        )
        savedStateHandle[STATE_KEY_SESSION_NAME] = name
    }

    private fun updateDescriptionState(description: String) {
        _addEditSessionState.value = uiState.value.copy(
            description = description
        )
        savedStateHandle[STATE_KEY_SESSION_DESCRIPTION] = description
    }

    sealed class AddEditSessionUiEvent {
        object NavBack : AddEditSessionUiEvent()
        data class ShowMessage(val message: String) : AddEditSessionUiEvent()
    }
}