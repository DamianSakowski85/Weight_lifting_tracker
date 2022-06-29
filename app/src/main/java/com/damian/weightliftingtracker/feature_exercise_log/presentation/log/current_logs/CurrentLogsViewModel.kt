package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.current_logs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.ExerciseLogUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

const val STATE_KEY_EXERCISE_ID = "exerciseId"

@HiltViewModel
class CurrentLogsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exerciseLogUseCases: ExerciseLogUseCases
) : ViewModel() {

    private val _currentLogsState = mutableStateOf(
        CurrentLogsState()
    )
    val currentLogState: State<CurrentLogsState> = _currentLogsState

    private var getCurrentLogsJob: Job? = null

    init {
        savedStateHandle.get<Int>(STATE_KEY_EXERCISE_ID)?.let {
            val date = LocalDate.now().toString()
            _currentLogsState.value = currentLogState.value.copy(
                exerciseId = it
            )
            getCurrentLogs(it, date)
        }
    }

    fun onEvent(event: CurrentExerciseLogEvent) {
        when (event) {
            is CurrentExerciseLogEvent.OnDelete -> {
                _currentLogsState.value = currentLogState.value.copy(
                    currentLogToDelete = event.log,
                    isDeleteDialogVisible = true
                )
            }
            is CurrentExerciseLogEvent.OnDismissDialog -> {
                _currentLogsState.value = currentLogState.value.copy(
                    currentLogToDelete = null,
                    isDeleteDialogVisible = false
                )
            }
            is CurrentExerciseLogEvent.OnConfirmDeletion -> {
                _currentLogsState.value.currentLogToDelete?.let {
                    viewModelScope.launch {
                        exerciseLogUseCases.deleteSelectedExerciseLogUseCase(it)
                        _currentLogsState.value = currentLogState.value.copy(
                            currentLogToDelete = null,
                            isDeleteDialogVisible = false
                        )
                    }
                }
            }
        }
    }

    private fun getCurrentLogs(exerciseId: Int, date: String) {
        getCurrentLogsJob?.cancel()
        getCurrentLogsJob = exerciseLogUseCases
            .currentExerciseLogsUseCase(exerciseId, date).onEach { logs ->
                if (logs.isNotEmpty()) {
                    val currentVolume = exerciseLogUseCases.calculateVolumeUseCase(logs)
                    _currentLogsState.value = currentLogState.value.copy(
                        logs = logs,
                        isCurrentLogEmpty = false,
                        isVolumeVisible = true,
                        weightVolume = currentVolume.weightVolume,
                        pauseVolume = currentVolume.pauseVolume
                    )
                } else {
                    _currentLogsState.value = currentLogState.value.copy(
                        logs = logs,
                        isCurrentLogEmpty = true,
                        isVolumeVisible = false
                    )
                }
            }.launchIn(viewModelScope)
    }
}