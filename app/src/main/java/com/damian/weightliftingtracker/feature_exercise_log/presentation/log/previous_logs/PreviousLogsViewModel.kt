package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.previous_logs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.ExerciseLogUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

const val STATE_KEY_EXERCISE_ID = "exerciseId"

@HiltViewModel
class PreviousLogsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exerciseLogUseCases: ExerciseLogUseCases
) : ViewModel() {

    private val _previousLogsState = mutableStateOf(
        PreviousLogsState()
    )
    val previousLogsState: State<PreviousLogsState> = _previousLogsState

    init {
        savedStateHandle.get<Int>(STATE_KEY_EXERCISE_ID)?.let {
            val date = LocalDate.now().toString()
            _previousLogsState.value = previousLogsState.value.copy(
                exerciseId = it
            )
            viewModelScope.launch {
                getPreviousLogs(it, date)
            }
        }
    }

    private suspend fun getPreviousLogs(exerciseId: Int, date: String) {
        exerciseLogUseCases.previousExerciseLogsUseCase(
            exerciseId,
            date
        ).collect { logs ->
            if (logs.isNotEmpty()) {
                val previousVolume = exerciseLogUseCases.calculateVolumeUseCase(
                    logs
                )
                _previousLogsState.value = previousLogsState.value.copy(
                    logs = logs,
                    previousDate = logs.first().date,
                    isPreviousLogEmpty = false,
                    isVolumeVisible = true,
                    weightVolume = previousVolume.weightVolume,
                    pauseVolume = previousVolume.pauseVolume
                )
            }
        }
    }
}