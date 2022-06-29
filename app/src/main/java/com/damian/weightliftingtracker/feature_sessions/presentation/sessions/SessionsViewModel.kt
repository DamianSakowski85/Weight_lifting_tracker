package com.damian.weightliftingtracker.feature_sessions.presentation.sessions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.SessionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_PLAN_ID = "planId"
const val STATE_KEY_PLAN_NAME = "planName"

@HiltViewModel
class SessionsViewModel @Inject constructor(
    private val sessionUseCases: SessionUseCases,
    savedStateHandle: SavedStateHandle,
    private val stringProvider: StringProvider,
) : ViewModel() {

    private val _sessionsState = mutableStateOf(SessionsState())
    val uiState: State<SessionsState> = _sessionsState

    private var getSessionsJob: Job? = null

    init {
        val planName = savedStateHandle.get<String>(STATE_KEY_PLAN_NAME)
        planName?.let {
            _sessionsState.value = uiState.value.copy(
                planName = it
            )
        }
        val planId = savedStateHandle.get<Int>(STATE_KEY_PLAN_ID)
        planId?.let {
            getSessions(it)
            _sessionsState.value = uiState.value.copy(
                planId = it
            )
        }
    }

    fun onEvent(event: SessionsEvent) {
        when (event) {
            is SessionsEvent.OnAdd -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        SessionsUiEvent.NavToAddSession(
                            uiState.value.planId
                        )
                    )
                }
            }
            is SessionsEvent.OnEdit -> {
                _sessionsState.value.selectedSession?.let {
                    viewModelScope.launch {
                        _eventFlow.emit(SessionsUiEvent.CloseContextMenu)
                        _eventFlow.emit(
                            SessionsUiEvent.NavToEditSession(
                                uiState.value.planId,
                                it.id
                            )
                        )
                    }
                }
            }
            is SessionsEvent.OnSessionClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        SessionsUiEvent.NavToDetails(
                            event.session.id,
                            event.session.sessionName
                        )
                    )
                }
            }
            is SessionsEvent.OnConfirmDeletion -> {
                deleteSelectedSession()
            }
            is SessionsEvent.OnDismissDialog -> {
                _sessionsState.value = uiState.value.copy(
                    isDeleteDialogVisible = false,
                    selectedSession = null
                )
            }
            is SessionsEvent.OnDelete -> {
                viewModelScope.launch {
                    _eventFlow.emit(SessionsUiEvent.CloseContextMenu)

                    _sessionsState.value = uiState.value.copy(
                        isDeleteDialogVisible = true
                    )
                }
            }
            is SessionsEvent.OnBackPressed -> {
                viewModelScope.launch {
                    _eventFlow.emit(SessionsUiEvent.PopBackStack)
                }
            }
            is SessionsEvent.OnSessionMenuClick -> {
                _sessionsState.value = uiState.value.copy(
                    selectedSession = event.selectedSession
                )
                viewModelScope.launch {
                    _eventFlow.emit(SessionsUiEvent.OpenContextMenu(event.selectedSession.sessionName))
                }
            }
        }
    }

    private fun getSessions(planId: Int) {
        getSessionsJob?.cancel()
        getSessionsJob = sessionUseCases.getSessionsUseCase(planId).onEach { sessions ->
            if (sessions.isEmpty()) {
                _sessionsState.value = uiState.value.copy(
                    sessions = sessions,
                    isEmptyListLabelVisible = true
                )
            } else {
                _sessionsState.value = uiState.value.copy(
                    sessions = sessions,
                    isEmptyListLabelVisible = false
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteSelectedSession() {
        viewModelScope.launch {
            val sessionToDelete = _sessionsState.value.selectedSession

            sessionToDelete?.let {
                kotlin.runCatching {
                    sessionUseCases.deleteSessionUseCase(it)
                }.onSuccess {
                    _sessionsState.value = uiState.value.copy(
                        selectedSession = null,
                        isDeleteDialogVisible = false
                    )
                    _eventFlow.emit(
                        SessionsUiEvent.ShowMessage(
                            message = "${sessionToDelete.sessionName} " + stringProvider.getString(
                                R.string.deleted
                            )
                        )
                    )
                }.onFailure {
                    _sessionsState.value = uiState.value.copy(
                        selectedSession = null,
                        isDeleteDialogVisible = false
                    )
                    _eventFlow.emit(
                        SessionsUiEvent.ShowMessage(
                            message = it.message.toString()
                        )
                    )
                }
            }
        }
    }

    private val _eventFlow = MutableSharedFlow<SessionsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class SessionsUiEvent {
        object PopBackStack : SessionsUiEvent()
        data class ShowMessage(val message: String) : SessionsUiEvent()
        data class OpenContextMenu(val sessionName: String) : SessionsUiEvent()
        object CloseContextMenu : SessionsUiEvent()
        data class NavToAddSession(val planId: Int) : SessionsUiEvent()
        data class NavToEditSession(val planId: Int, val sessionId: Int) : SessionsUiEvent()
        data class NavToDetails(val sessionId: Int, val sessionName: String) : SessionsUiEvent()
    }
}