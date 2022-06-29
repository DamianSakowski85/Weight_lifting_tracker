package com.damian.weightliftingtracker.feature_exercise.presentation.exercises

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.use_case.ExerciseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exerciseUseCases: ExerciseUseCases,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _exerciseState = mutableStateOf(ExerciseState())
    val exerciseState: State<ExerciseState> = _exerciseState

    private var getExerciseJob: Job? = null

    init {
        val sessionName = savedStateHandle.get<String>("sessionName")
        sessionName?.let {
            _exerciseState.value = exerciseState.value.copy(
                sessionName = it
            )
        }
        val sessionId = savedStateHandle.get<Int>("sessionId")
        sessionId?.let {
            getExercises(it)
            _exerciseState.value = exerciseState.value.copy(
                sessionId = it
            )
        }
    }

    fun onEvent(event: ExerciseEvent) {
        when (event) {
            is ExerciseEvent.OnAdd -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavToAddExercise(_exerciseState.value.sessionId))
                }
            }
            is ExerciseEvent.OnBackPressed -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.PopBackStack)
                }
            }
            is ExerciseEvent.OnConfirmDeletion -> {
                deleteSelectedExercise()
            }

            is ExerciseEvent.OnMenuClick -> {
                _exerciseState.value = exerciseState.value.copy(
                    selectedExercise = event.exercise,
                )
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OpenContextMenu(event.exercise.exerciseName))
                }
            }
            is ExerciseEvent.OnDismissDialog -> {
                _exerciseState.value = exerciseState.value.copy(
                    selectedExercise = null,
                    isDeleteDialogVisible = false
                )
            }
            is ExerciseEvent.OnEdit -> {
                _exerciseState.value.selectedExercise?.let {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.CloseContextMenu)
                        _eventFlow.emit(
                            UiEvent.NavToEditExercise(
                                _exerciseState.value.sessionId,
                                it.id
                            )
                        )
                    }
                }
            }
            is ExerciseEvent.OnDelete -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CloseContextMenu)
                }
                _exerciseState.value = exerciseState.value.copy(
                    isDeleteDialogVisible = true
                )
            }
            is ExerciseEvent.OnShowBarCharts -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CloseContextMenu)

                    _exerciseState.value.selectedExercise?.let {
                        _eventFlow.emit(UiEvent.NavToLineCharts(it))
                    }
                }
            }
            is ExerciseEvent.OnShowDatePicker -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CloseContextMenu)

                    _exerciseState.value.selectedExercise?.let {
                        _eventFlow.emit(UiEvent.NavToDatePicker(it.id, it.exerciseName))
                    }
                }
            }

            is ExerciseEvent.OnExerciseClick -> {
                _exerciseState.value = exerciseState.value.copy(
                    selectedExercise = event.exercise
                )
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.NavToExerciseLog(
                            event.exercise.id,
                            event.exercise.exerciseName
                        )
                    )
                }
            }

            is ExerciseEvent.OnClearExerciseData -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CloseContextMenu)
                }
                _exerciseState.value = exerciseState.value.copy(
                    isClearHistoryDialogVisible = true
                )
            }
            is ExerciseEvent.OnConfirmDataClearance -> {
                viewModelScope.launch {
                    kotlin.runCatching {
                        _exerciseState.value.selectedExercise?.let {
                            exerciseUseCases.clearExerciseDataUseCase(it.id)
                        }

                    }.onSuccess {
                        _exerciseState.value = exerciseState.value.copy(
                            selectedExercise = null,
                            isClearHistoryDialogVisible = false
                        )

                        _eventFlow.emit(
                            UiEvent.ShowMessage(
                                stringProvider.getString(R.string.history_cleared)
                            )
                        )
                    }.onFailure {
                        _exerciseState.value = exerciseState.value.copy(
                            selectedExercise = null,
                            isClearHistoryDialogVisible = false
                        )

                        _eventFlow.emit(UiEvent.ShowMessage(it.message.toString()))
                    }
                }
            }
            is ExerciseEvent.OnDismissDataClearance -> {
                _exerciseState.value = exerciseState.value.copy(
                    selectedExercise = null,
                    isClearHistoryDialogVisible = false
                )
            }
        }
    }

    private fun deleteSelectedExercise() {
        viewModelScope.launch {
            val exerciseToDelete = _exerciseState.value.selectedExercise

            exerciseToDelete?.let {
                kotlin.runCatching {
                    exerciseUseCases.deleteExerciseUseCase(it)
                }.onSuccess {
                    _exerciseState.value = exerciseState.value.copy(
                        selectedExercise = null,
                        isDeleteDialogVisible = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            stringProvider.getString(R.string.deleted)
                        )
                    )
                }.onFailure {
                    _exerciseState.value = exerciseState.value.copy(
                        selectedExercise = null,
                        isDeleteDialogVisible = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            it.message.toString()
                        )
                    )
                }
            }
        }
    }

    private fun getExercises(sessionId: Int) {
        getExerciseJob?.cancel()
        getExerciseJob = exerciseUseCases.getExercisesUseCase(sessionId).onEach { exercises ->
            if (exercises.isEmpty()) {
                _exerciseState.value = exerciseState.value.copy(
                    exercises = exercises,
                    isEmptyListLabelVisible = true
                )
            } else {
                _exerciseState.value = exerciseState.value.copy(
                    exercises = exercises,
                    isEmptyListLabelVisible = false
                )
            }
        }.launchIn(viewModelScope)
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object PopBackStack : UiEvent()
        data class ShowMessage(val message: String) : UiEvent()
        data class NavToAddExercise(val sessionId: Int) : UiEvent()
        data class NavToEditExercise(val sessionId: Int, val exerciseId: Int) : UiEvent()
        data class NavToExerciseLog(val exerciseId: Int, val exerciseName: String) : UiEvent()
        data class OpenContextMenu(val exerciseName: String) : UiEvent()
        object CloseContextMenu : UiEvent()
        data class NavToLineCharts(val exercise: Exercise) : UiEvent()
        data class NavToDatePicker(val exerciseId: Int, val exerciseName: String) : UiEvent()
    }
}