package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.utli

const val LINE_CHART_VOLUME_SCREEN = "line_chart_volume_screen"

sealed class VolumeScreens(val route: String) {
    object VolumeScreen : VolumeScreens(LINE_CHART_VOLUME_SCREEN)
}
