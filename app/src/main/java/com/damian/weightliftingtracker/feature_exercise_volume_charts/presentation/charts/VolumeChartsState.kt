package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.charts

import com.madrapps.plot.line.DataPoint

data class VolumeChartsState(
    val exerciseName: String = "",
    val showVolumeChart: Boolean = false,
    val showPauseChart: Boolean = false,
    val isBarDataEmpty: Boolean = true,

    val volumeTest: List<DataPoint>? = null,
    val pauseTime: List<DataPoint>? = null
)