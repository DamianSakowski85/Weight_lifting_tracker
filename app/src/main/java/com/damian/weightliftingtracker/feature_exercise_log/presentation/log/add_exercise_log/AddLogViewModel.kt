package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.InvalidExerciseLogException
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.ExerciseLogUseCases
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.InvalidPauseFormatException
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.InvalidRepsFormatException
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.InvalidWeightFormatException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

const val STATE_KEY_EXERCISE_ID = "exerciseId"
const val STATE_KEY_EXERCISE_NAME = "exerciseName"
const val STATE_KEY_WEIGHT_GOAL = "weight_goal"
const val STATE_KEY_REPS_GOAL = "reps_goal"
const val STATE_KEY_PAUSE_GOAL = "pause_goal"
const val STATE_KEY_WEIGHT_ACHIEVED = "weight_achieved"
const val STATE_KEY_REPS_ACHIEVED = "reps_achieved"
const val STATE_KEY_PAUSE_ACHIEVED = "pause_achieved"

@HiltViewModel
class AddLogViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stringProvider: StringProvider,
    private val exerciseLogUseCases: ExerciseLogUseCases
) : ViewModel() {

    private val _addExerciseLogState = mutableStateOf(
        AddLogState()
    )
    val addExerciseLogState: State<AddLogState> = _addExerciseLogState

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(STATE_KEY_EXERCISE_ID)?.let {
                _addExerciseLogState.value = addExerciseLogState.value.copy(
                    exerciseId = it
                )

                val lastSet = exerciseLogUseCases.getLastSetUseCase(it)
                lastSet?.let {
                    updateWeightGoal(lastSet.weightAchieved.toString())
                    updateRepsGoal(lastSet.repsAchieved.toString())
                    updatePauseGoal(lastSet.pauseAchieved.toString())
                }

            }
            savedStateHandle.get<String>(STATE_KEY_EXERCISE_NAME)?.let {
                _addExerciseLogState.value = addExerciseLogState.value.copy(
                    exerciseName = it
                )
            }
        }
    }

    fun onEvent(event: AddLogEvent) {
        when (event) {
            is AddLogEvent.OnClose -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavBack)
                }
            }
            is AddLogEvent.OnEnterWeightGoal -> {
                updateWeightGoal(event.value)
            }
            is AddLogEvent.OnIncreaseWeightGoalValue -> {
                try {
                    val increasedWeightValue = exerciseLogUseCases.increaseWeightValueUseCase(
                        addExerciseLogState.value.weightGoal
                    )
                    updateWeightGoal(increasedWeightValue)
                } catch (exception: InvalidWeightFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }
            is AddLogEvent.OnDecreaseWeightGoalValue -> {
                try {
                    val decreaseWeightValue = exerciseLogUseCases.decreaseWeightValueUseCase(
                        addExerciseLogState.value.weightGoal
                    )
                    updateWeightGoal(decreaseWeightValue)
                } catch (exception: InvalidWeightFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }

            is AddLogEvent.OnEnterRepsGoal -> {
                updateRepsGoal(event.value)
            }
            is AddLogEvent.OnIncreaseRepsGoalValue -> {
                try {
                    val increasedRepsValue = exerciseLogUseCases.increaseRepsValueUseCase(
                        addExerciseLogState.value.repsGoal
                    )
                    updateRepsGoal(increasedRepsValue)
                } catch (exception: InvalidRepsFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }
            is AddLogEvent.OnDecreaseRepsGoalValue -> {
                try {
                    val decreasedRepsValue = exerciseLogUseCases.decreaseRepsValueUseCase(
                        addExerciseLogState.value.repsGoal
                    )
                    updateRepsGoal(decreasedRepsValue)
                } catch (exception: InvalidRepsFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }

            is AddLogEvent.OnEnterPauseGoal -> {
                updatePauseGoal(event.value)
            }
            is AddLogEvent.OnIncreasePauseGoalValue -> {
                try {
                    val increasedValue = exerciseLogUseCases.increasePauseValueUseCase(
                        addExerciseLogState.value.pauseGoal
                    )
                    updatePauseGoal(increasedValue)
                } catch (exception: InvalidPauseFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }
            is AddLogEvent.OnDecreasePauseGoalValue -> {
                try {
                    val decreasedValue = exerciseLogUseCases.decreasePauseValueUseCase(
                        addExerciseLogState.value.pauseGoal
                    )
                    updatePauseGoal(decreasedValue)
                } catch (exception: InvalidPauseFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }

            is AddLogEvent.OnEnterWeightAchieved -> {
                updateWeightAchieved(event.value)
            }
            is AddLogEvent.OnIncreaseWeightAchievedValue -> {
                try {
                    val increasedWeightValue = exerciseLogUseCases.increaseWeightValueUseCase(
                        addExerciseLogState.value.weightAchieved
                    )
                    updateWeightAchieved(increasedWeightValue)
                } catch (exception: InvalidWeightFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }
            is AddLogEvent.OnDecreaseWeightAchievedValue -> {
                try {
                    val decreasedWeightValue = exerciseLogUseCases.decreaseWeightValueUseCase(
                        addExerciseLogState.value.weightAchieved
                    )
                    updateWeightAchieved(decreasedWeightValue)
                } catch (exception: InvalidWeightFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }

            is AddLogEvent.OnEnterRepsAchieved -> {
                updateRepsAchieved(event.value)
            }
            is AddLogEvent.OnIncreaseRepsAchievedValue -> {
                try {
                    val increasedRepsValue = exerciseLogUseCases.increaseRepsValueUseCase(
                        addExerciseLogState.value.repsAchieved
                    )
                    updateRepsAchieved(increasedRepsValue)
                } catch (exception: InvalidRepsFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }
            is AddLogEvent.OnDecreaseRepsAchievedValue -> {
                try {
                    val decreasedRepsValue = exerciseLogUseCases.decreaseRepsValueUseCase(
                        addExerciseLogState.value.repsAchieved
                    )
                    updateRepsAchieved(decreasedRepsValue)
                } catch (exception: InvalidRepsFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }

            is AddLogEvent.OnEnterPauseAchieved -> {
                updatePauseAchieved(event.value)
            }
            is AddLogEvent.OnIncreasePauseAchievedValue -> {
                try {
                    val increasedPauseValue = exerciseLogUseCases.increasePauseValueUseCase(
                        addExerciseLogState.value.pauseAchieved
                    )
                    updatePauseAchieved(increasedPauseValue)
                } catch (exception: InvalidPauseFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }
            is AddLogEvent.OnDecreasePauseAchievedValue -> {
                try {
                    val decreasedPauseValue = exerciseLogUseCases.decreasePauseValueUseCase(
                        addExerciseLogState.value.pauseAchieved
                    )
                    updatePauseAchieved(decreasedPauseValue)
                } catch (exception: InvalidPauseFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowMessage(exception.message.toString()))
                    }
                }
            }

            is AddLogEvent.OnSave -> {
                saveExerciseLog()
            }

            is AddLogEvent.UpdateTimer -> {
                _addExerciseLogState.value = addExerciseLogState.value.copy(
                    pauseTimerValue = "Pause remaining (sec): " + event.timerValue,
                )
            }

            is AddLogEvent.PauseFinished -> {
                _addExerciseLogState.value = addExerciseLogState.value.copy(
                    isTimerVisible = false,
                    isCompleteSetActive = true
                )
            }
        }
    }

    private fun saveExerciseLog() {
        viewModelScope.launch {
            try {
                _addExerciseLogState.value.exerciseId?.let {

                    val currentLog = exerciseLogUseCases.addExerciseLogUseCase(
                        it,
                        _addExerciseLogState.value.weightGoal,
                        _addExerciseLogState.value.repsGoal,
                        _addExerciseLogState.value.pauseGoal,
                        _addExerciseLogState.value.weightAchieved,
                        _addExerciseLogState.value.repsAchieved,
                        _addExerciseLogState.value.pauseAchieved,
                        LocalDate.now().toString()
                    )

                    updateWeightAchieved(currentLog.weightAchieved.toString())
                    updateRepsAchieved(currentLog.repsAchieved.toString())
                    updatePauseAchieved(currentLog.pauseAchieved.toString())

                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            stringProvider.getString(R.string.set_added)
                        )
                    )

                    _addExerciseLogState.value = addExerciseLogState.value.copy(
                        isTimerVisible = true,
                        isCompleteSetActive = false
                    )

                    val pauseLeftInMilliseconds = (_addExerciseLogState.value.pauseGoal.toLong() * 1000)
                    _eventFlow.emit(UiEvent.LaunchTimer(
                        pauseLeftInMilliseconds
                    ))
                }

            } catch (exception: InvalidExerciseLogException) {
                _eventFlow.emit(
                    UiEvent.ShowMessage(
                        message = exception.message
                            ?: stringProvider.getString(R.string.could_not_save_log)
                    )
                )
            }
        }
    }

    private fun updateWeightGoal(weight: String) {
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            weightGoal = weight
        )
        savedStateHandle[STATE_KEY_WEIGHT_GOAL] = weight
    }

    private fun updateRepsGoal(reps: String) {
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            repsGoal = reps
        )
        savedStateHandle[STATE_KEY_REPS_GOAL] = reps
    }

    private fun updatePauseGoal(pause: String) {
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            pauseGoal = pause
        )
        savedStateHandle[STATE_KEY_PAUSE_GOAL] = pause
    }

    private fun updateWeightAchieved(weight: String) {
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            weightAchieved = weight
        )
        savedStateHandle[STATE_KEY_WEIGHT_ACHIEVED] = weight
    }

    private fun updateRepsAchieved(reps: String) {
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            repsAchieved = reps
        )
        savedStateHandle[STATE_KEY_REPS_ACHIEVED] = reps
    }

    private fun updatePauseAchieved(pause: String) {
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            pauseAchieved = pause
        )
        savedStateHandle[STATE_KEY_PAUSE_ACHIEVED] = pause
    }
    private fun hideTimer(){
        _addExerciseLogState.value = addExerciseLogState.value.copy(
            isTimerVisible = false
        )
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object NavBack : UiEvent()
        data class ShowMessage(val message: String) : UiEvent()
        data class LaunchTimer(val timeLeftInMilliseconds : Long) : UiEvent()
    }
}