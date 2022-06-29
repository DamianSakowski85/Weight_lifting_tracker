package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.charts

sealed class VolumeChartsEvent {
    object OnBackPressed : VolumeChartsEvent()
}