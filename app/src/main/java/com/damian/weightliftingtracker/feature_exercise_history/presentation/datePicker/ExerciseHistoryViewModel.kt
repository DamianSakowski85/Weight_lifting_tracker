package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.ExerciseHistoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_EXERCISE_ID = "exerciseId"
const val STATE_KEY_EXERCISE_NAME = "exerciseName"

@HiltViewModel
class ExerciseHistoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val exerciseHistoryUseCases: ExerciseHistoryUseCases
) : ViewModel() {

    private val _exerciseHistoryState = mutableStateOf(
        ExerciseHistoryState()
    )
    val exerciseHistoryState: State<ExerciseHistoryState> = _exerciseHistoryState

    init {
        savedStateHandle.get<Int>(STATE_KEY_EXERCISE_ID)?.let {
            _exerciseHistoryState.value = exerciseHistoryState.value.copy(
                exerciseId = it,
                exerciseName = savedStateHandle.get<String>(STATE_KEY_EXERCISE_NAME) ?: ""
            )
            viewModelScope.launch {
                loadCalendarConstrain(it)
                getLogs(it, null)
            }

        }
    }

    private suspend fun getLogs(exerciseId: Int, date: String?) {
        exerciseHistoryUseCases.loadLogUseCase(exerciseId, date).collect { logs ->
            if (logs.isNotEmpty()) {

                val previousVolume = exerciseHistoryUseCases.calculateVolumeUseCase(
                    logs
                )

                _exerciseHistoryState.value = exerciseHistoryState.value.copy(
                    logs = logs,
                    date = logs.first().date,
                    isLogEmpty = false,
                    weightVolume = previousVolume.weightVolume,
                    pauseVolume = previousVolume.pauseVolume
                )
            }

        }
    }

    private suspend fun loadCalendarConstrain(exerciseId: Int) {
        val calendarConstraints = exerciseHistoryUseCases.getCalendarConstrainUseCase(
            exerciseId
        )
        _exerciseHistoryState.value = exerciseHistoryState.value.copy(
            calendarConstraintsModel = calendarConstraints
        )
    }

    fun onEvent(event: ExerciseHistoryEvent) {
        when (event) {
            is ExerciseHistoryEvent.OnPickDate -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowDatePicker)
                }
            }
            is ExerciseHistoryEvent.OnDateClick -> {
                viewModelScope.launch {
                    _exerciseHistoryState.value.exerciseId?.let {
                        getLogs(it, event.date)
                    }
                }
            }
            is ExerciseHistoryEvent.OnBackPressed -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavBack)
                }
            }
        }
    }


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object NavBack : UiEvent()
        object ShowDatePicker : UiEvent()
    }
}