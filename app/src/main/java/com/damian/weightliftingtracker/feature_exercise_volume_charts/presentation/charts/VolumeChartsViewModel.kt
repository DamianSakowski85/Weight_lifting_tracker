package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.charts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case.GetVolumeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolumeChartsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val volumeUseCase: GetVolumeListUseCase
) : ViewModel() {
    private val _volumeChartsState = mutableStateOf(VolumeChartsState())
    val volumeChartsState: State<VolumeChartsState> = _volumeChartsState

    init {
        val exerciseId = savedStateHandle.get<Int>("exerciseId")
        val exerciseName = savedStateHandle.get<String>("exerciseName") ?: ""
        _volumeChartsState.value = volumeChartsState.value.copy(
            exerciseName = exerciseName
        )
        exerciseId?.let {
            viewModelScope.launch {
                getVolumeData(it)
            }
        }
    }

    private suspend fun getVolumeData(exerciseId: Int) {
        val volumeList = volumeUseCase.invoke(exerciseId)
        if (volumeList[0].size > 1) {
            _volumeChartsState.value = volumeChartsState.value.copy(
                volumeTest = volumeList[0],
                showVolumeChart = true,
                isBarDataEmpty = false
            )
        }

        if (volumeList[1].size > 1) {
            _volumeChartsState.value = volumeChartsState.value.copy(
                pauseTime = volumeList[1],
                showPauseChart = true,
                isBarDataEmpty = false
            )
        }

        if (volumeList[0].size <= 1 && volumeList[1].size <= 1) {
            _volumeChartsState.value = volumeChartsState.value.copy(
                showVolumeChart = false,
                showPauseChart = false,
                isBarDataEmpty = true
            )
        }
    }

    fun onEvent(event: VolumeChartsEvent) {
        when (event) {
            is VolumeChartsEvent.OnBackPressed -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.PopBackStack)
                }
            }
        }
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object PopBackStack : UiEvent()
    }
}