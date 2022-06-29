package com.damian.weightliftingtracker.feature_exercise.domain.use_case

data class ExerciseUseCases(
    val addExerciseUseCase: AddExerciseUseCase,
    val updateExerciseUseCase: UpdateExerciseUseCase,
    val deleteExerciseUseCase: DeleteExerciseUseCase,
    val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    val getExercisesUseCase: GetExercisesUseCase,
    val clearExerciseDataUseCase: ClearExerciseDataUseCase
)
