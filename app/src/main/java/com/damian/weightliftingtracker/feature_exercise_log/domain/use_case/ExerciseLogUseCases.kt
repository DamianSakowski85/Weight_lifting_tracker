package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

data class ExerciseLogUseCases(
    val addExerciseLogUseCase: AddExerciseLogUseCase,
    val currentExerciseLogsUseCase: CurrentExerciseLogsUseCase,
    val previousExerciseLogsUseCase: PreviousExerciseLogsUseCase,
    val deleteSelectedExerciseLogUseCase: DeleteSelectedExerciseLogUseCase,
    val increaseWeightValueUseCase: IncreaseWeightValueUseCase,
    val decreaseWeightValueUseCase: DecreaseWeightValueUseCase,
    val increaseRepsValueUseCase: IncreaseRepsValueUseCase,
    val decreaseRepsValueUseCase: DecreaseRepsValueUseCase,
    val increasePauseValueUseCase: IncreasePauseValueUseCase,
    val decreasePauseValueUseCase: DecreasePauseValueUseCase,
    val calculateVolumeUseCase: CalculateVolumeUseCase,
    val getLastSetUseCase: GetLastSetUseCase
)

class InvalidWeightFormatException(message: String) : java.lang.Exception(message)
class InvalidRepsFormatException(message: String) : java.lang.Exception(message)
class InvalidPauseFormatException(message: String) : java.lang.Exception(message)
