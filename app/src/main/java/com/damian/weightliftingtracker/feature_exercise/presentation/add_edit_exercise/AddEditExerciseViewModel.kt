package com.damian.weightliftingtracker.feature_exercise.presentation.add_edit_exercise

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.use_case.ExerciseUseCases
import com.damian.weightliftingtracker.feature_sessions.domain.model.InvalidSessionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_SESSION_NAME = "entered_session_name"
const val STATE_KEY_SESSION_DESCRIPTION = "entered_session_description"
const val STATE_KEY_SESSION_ID = "sessionId"
const val STATE_KEY_EXERCISE_ID = "exerciseId"

@HiltViewModel
class AddEditExerciseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stringProvider: StringProvider,
    private val exerciseUseCases: ExerciseUseCases
) : ViewModel() {

    private var _currentExercise: Exercise? = null
    private val _addEditExerciseState = mutableStateOf(
        AddEditExerciseState()
    )
    val addEditExerciseState: State<AddEditExerciseState> = _addEditExerciseState

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(STATE_KEY_EXERCISE_ID)?.let {
                _currentExercise = exerciseUseCases.getExerciseByIdUseCase(it)
            }
            _addEditExerciseState.value = addEditExerciseState.value.copy(
                name = savedStateHandle.get<String>(STATE_KEY_SESSION_NAME)
                    ?: _currentExercise?.exerciseName ?: "",

                description = savedStateHandle.get<String>(STATE_KEY_SESSION_DESCRIPTION)
                    ?: _currentExercise?.exerciseDescription ?: "",

                sessionId = savedStateHandle.get<Int>(STATE_KEY_SESSION_ID)
            )
        }
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddEditExerciseEvent) {
        when (event) {
            is AddEditExerciseEvent.OnEnterDescription -> {
                updateDescriptionState(event.value)
            }
            is AddEditExerciseEvent.OnEnterName -> {
                updateNameState(event.value)
            }
            is AddEditExerciseEvent.OnCloseClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavBack)
                }
            }
            is AddEditExerciseEvent.OnSaveSession -> {
                saveExercise()
            }
        }
    }

    private fun saveExercise() {
        viewModelScope.launch {
            try {
                _currentExercise?.let {
                    val exerciseToUpdate = it.copy(
                        exerciseName = _addEditExerciseState.value.name,
                        exerciseDescription = _addEditExerciseState.value.description
                    )
                    exerciseUseCases.updateExerciseUseCase(exerciseToUpdate)
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            stringProvider.getString(R.string.exercise_updated)
                        )
                    )
                } ?: run {
                    _addEditExerciseState.value.sessionId?.let {
                        exerciseUseCases.addExerciseUseCase(
                            Exercise(
                                sessionId = it,
                                exerciseName = _addEditExerciseState.value.name,
                                exerciseDescription = _addEditExerciseState.value.description
                            )
                        )
                    }
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            stringProvider.getString(R.string.exercise_added)
                        )
                    )
                }

            } catch (exception: InvalidSessionException) {
                _eventFlow.emit(
                    UiEvent.ShowMessage(
                        message = exception.message
                            ?: stringProvider.getString(R.string.could_not_save_exercise)
                    )
                )
            }
            _eventFlow.emit(UiEvent.NavBack)
        }
    }

    private fun updateNameState(name: String) {
        _addEditExerciseState.value = addEditExerciseState.value.copy(
            name = name
        )
        savedStateHandle[STATE_KEY_SESSION_NAME] = name
    }

    private fun updateDescriptionState(description: String) {
        _addEditExerciseState.value = addEditExerciseState.value.copy(
            description = description
        )
        savedStateHandle[STATE_KEY_SESSION_DESCRIPTION] = description
    }

    sealed class UiEvent {
        object NavBack : UiEvent()
        data class ShowMessage(val message: String) : UiEvent()
    }
}