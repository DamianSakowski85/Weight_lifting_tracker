package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

data class ExerciseHistoryUseCases(
    val loadLogUseCase: LoadLogUseCase,
    val calculateVolumeUseCase: CalculateVolumeUseCase,
    val getCalendarConstrainUseCase: GetCalendarConstrainUseCase
)
