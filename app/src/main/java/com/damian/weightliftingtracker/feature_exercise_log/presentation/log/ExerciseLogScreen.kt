package com.damian.weightliftingtracker.feature_exercise_log.presentation.log

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.presentation.components.DeleteDialog
import com.damian.weightliftingtracker.core.presentation.components.EmptyListLabel
import com.damian.weightliftingtracker.core.presentation.components.Header
import com.damian.weightliftingtracker.core.presentation.components.PreviousExerciseLogItem
import com.damian.weightliftingtracker.core.utli.TestTags.PAUSE_ACHIEVED
import com.damian.weightliftingtracker.core.utli.TestTags.PAUSE_GOAL
import com.damian.weightliftingtracker.core.utli.TestTags.REPS_ACHIEVED
import com.damian.weightliftingtracker.core.utli.TestTags.REPS_GOAL
import com.damian.weightliftingtracker.core.utli.TestTags.WEIGHT_ACHIEVED
import com.damian.weightliftingtracker.core.utli.TestTags.WEIGHT_GOAL
import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.*
import com.damian.weightliftingtracker.feature_exercise_log.presentation.components.*
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.AddLogEvent
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.AddLogViewModel
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.current_logs.CurrentExerciseLogEvent
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.current_logs.CurrentLogsViewModel
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.previous_logs.PreviousLogsViewModel
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExerciseLogScreen(
    closeScreen: () -> Unit,
    addLogViewModel: AddLogViewModel,
    currentLogsViewModel: CurrentLogsViewModel,
    previousLogsViewModel: PreviousLogsViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        addLogViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddLogViewModel.UiEvent.NavBack -> {
                    keyboardController?.hide()
                    closeScreen()
                }
                is AddLogViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(
                        context, event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is AddLogViewModel.UiEvent.LaunchTimer -> {
                    object : CountDownTimer(event.timeLeftInMilliseconds, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            addLogViewModel.onEvent(AddLogEvent.UpdateTimer(
                                millisUntilFinished / 1000
                            ))
                        }

                        override fun onFinish() {
                            addLogViewModel.onEvent(AddLogEvent.PauseFinished)
                        }
                    }.start()
                }
            }
        }
    }

    currentLogsViewModel.currentLogState.value.currentLogToDelete?.let { toDelete ->
        DeleteDialog(
            stringResource(id = R.string.set) + " " + toDelete.setNumber.toString(),
            stringResource(id = R.string.delete_selected_set),
            currentLogsViewModel.currentLogState.value.isDeleteDialogVisible,
            onConfirmClick = {
                currentLogsViewModel.onEvent(CurrentExerciseLogEvent.OnConfirmDeletion)
            },
            onDismissClick = {
                currentLogsViewModel.onEvent(CurrentExerciseLogEvent.OnDismissDialog)
            },
            onDismissRequest = {
                currentLogsViewModel.onEvent(CurrentExerciseLogEvent.OnDismissDialog)
            }
        )
    }
    Scaffold(
        topBar = {
            LogTopAppBar(
                title = addLogViewModel.addExerciseLogState.value.exerciseName,
                onCloseScreen = {
                    addLogViewModel.onEvent(AddLogEvent.OnClose)
                }
            )
        },
        scaffoldState = scaffoldState
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Header(stringResource(R.string.current_set))
            }

            item {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.goal),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            LogInputData(
                                modifier = Modifier.testTag(WEIGHT_GOAL),
                                label = stringResource(id = R.string.weight),
                                value = addLogViewModel.addExerciseLogState.value.weightGoal,
                                onDecreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnDecreaseWeightGoalValue)
                                },
                                onEnterValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnEnterWeightGoal(it))
                                },
                                onIncreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnIncreaseWeightGoalValue)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number,
                                )
                            )

                            LogInputData(
                                modifier = Modifier.testTag(REPS_GOAL),
                                label = stringResource(id = R.string.reps),
                                value = addLogViewModel.addExerciseLogState.value.repsGoal,
                                onDecreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnDecreaseRepsGoalValue)
                                },
                                onEnterValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnEnterRepsGoal(it))
                                },
                                onIncreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnIncreaseRepsGoalValue)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number,
                                )
                            )

                            LogInputData(
                                modifier = Modifier.testTag(PAUSE_GOAL),
                                label = stringResource(id = R.string.pause),
                                value = addLogViewModel.addExerciseLogState.value.pauseGoal,
                                onDecreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnDecreasePauseGoalValue)
                                },
                                onEnterValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnEnterPauseGoal(it))
                                },
                                onIncreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnIncreasePauseGoalValue)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number,
                                )
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.achieved),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            LogInputData(
                                modifier = Modifier.testTag(WEIGHT_ACHIEVED),
                                label = stringResource(id = R.string.weight),
                                value = addLogViewModel.addExerciseLogState.value.weightAchieved,
                                onDecreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnDecreaseWeightAchievedValue)
                                },
                                onEnterValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnEnterWeightAchieved(it))
                                },
                                onIncreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnIncreaseWeightAchievedValue)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number,
                                )
                            )
                            LogInputData(
                                modifier = Modifier.testTag(REPS_ACHIEVED),
                                label = stringResource(id = R.string.reps),
                                value = addLogViewModel.addExerciseLogState.value.repsAchieved,
                                onDecreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnDecreaseRepsAchievedValue)
                                },
                                onEnterValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnEnterRepsAchieved(it))
                                },
                                onIncreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnIncreaseRepsAchievedValue)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number,
                                )
                            )

                            LogInputData(
                                modifier = Modifier.testTag(PAUSE_ACHIEVED),
                                label = stringResource(id = R.string.pause),
                                value = addLogViewModel.addExerciseLogState.value.pauseAchieved,
                                onDecreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnDecreasePauseAchievedValue)
                                },
                                onEnterValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnEnterPauseAchieved(it))
                                },
                                onIncreaseValue = {
                                    addLogViewModel.onEvent(AddLogEvent.OnIncreasePauseAchievedValue)
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number,
                                )
                            )
                        }

                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    AnimatedVisibility(
                        visible = addLogViewModel.addExerciseLogState.value.isTimerVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Row {
                            Text(
                                text = addLogViewModel.addExerciseLogState.value.pauseTimerValue,
                                modifier = Modifier.padding(16.dp,0.dp)
                            )
                        }

                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            keyboardController?.hide()
                            addLogViewModel.onEvent(AddLogEvent.OnSave)
                        },
                        enabled = addLogViewModel.addExerciseLogState.value.isCompleteSetActive
                    ) {
                        Text(text = stringResource(id = R.string.complete_set))
                    }

                }

                Spacer(modifier = Modifier.padding(8.dp))
            }

            item {
                Header(stringResource(R.string.current_session))
            }
            item {
                EmptyListLabel(
                    isListEmpty = currentLogsViewModel.currentLogState.value.isCurrentLogEmpty,
                    text = stringResource(id = R.string.no_current_session),
                    animationDuration = 0,
                    modifier = Modifier.fillMaxSize()
                )
            }

            items(currentLogsViewModel.currentLogState.value.logs) { log ->
                ExerciseLogItem(
                    exerciseLog = log,
                    modifier = Modifier.fillMaxWidth(),
                    onDeleteClick = {
                        currentLogsViewModel.onEvent(CurrentExerciseLogEvent.OnDelete(log))
                    }
                )
                Divider(startIndent = 0.dp)
            }
            item {
                LogSummaryItem(
                    isVisible = currentLogsViewModel.currentLogState.value.isVolumeVisible,
                    volume = currentLogsViewModel.currentLogState.value.weightVolume,
                    pause = currentLogsViewModel.currentLogState.value.pauseVolume
                )
            }

            item {
                Spacer(modifier = Modifier.padding(8.dp))
            }

            item {
                Header(
                    stringResource(R.string.previous_session) +
                            " : " +
                            previousLogsViewModel.previousLogsState.value.previousDate
                )
            }

            item {
                EmptyListLabel(
                    isListEmpty = previousLogsViewModel.previousLogsState.value.isPreviousLogEmpty,
                    text = stringResource(id = R.string.no_previous_session),
                    animationDuration = 0,
                    modifier = Modifier.fillMaxSize()
                )
            }

            items(previousLogsViewModel.previousLogsState.value.logs) { log ->
                PreviousExerciseLogItem(
                    exerciseLog = log,
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(startIndent = 0.dp)
            }

            item {
                LogSummaryItem(
                    isVisible = previousLogsViewModel.previousLogsState.value.isVolumeVisible,
                    volume = previousLogsViewModel.previousLogsState.value.weightVolume,
                    pause = previousLogsViewModel.previousLogsState.value.pauseVolume
                )
            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}


@Preview("Exercise Log Preview")
@Composable
fun ExerciseLogPreview() {
    val fakeRepo = FakeExerciseLogRepo()
    val savedStateHandle = SavedStateHandle()
    val stringProvider = StringProvider(LocalContext.current)
    val exerciseLogUseCases = ExerciseLogUseCases(
        addExerciseLogUseCase = AddExerciseLogUseCase(fakeRepo),
        calculateVolumeUseCase = CalculateVolumeUseCase(),
        currentExerciseLogsUseCase = CurrentExerciseLogsUseCase(fakeRepo),
        decreasePauseValueUseCase = DecreasePauseValueUseCase(),
        decreaseRepsValueUseCase = DecreaseRepsValueUseCase(),
        decreaseWeightValueUseCase = DecreaseWeightValueUseCase(),
        deleteSelectedExerciseLogUseCase = DeleteSelectedExerciseLogUseCase(fakeRepo),
        getLastSetUseCase = GetLastSetUseCase(fakeRepo),
        increasePauseValueUseCase = IncreasePauseValueUseCase(),
        increaseRepsValueUseCase = IncreaseRepsValueUseCase(),
        increaseWeightValueUseCase = IncreaseWeightValueUseCase(),
        previousExerciseLogsUseCase = PreviousExerciseLogsUseCase(fakeRepo)
    )

    Surface {
        WeightLiftingTrackerTheme {
            ExerciseLogScreen(
                closeScreen = {},
                addLogViewModel = AddLogViewModel(
                    savedStateHandle,
                    stringProvider,
                    exerciseLogUseCases = exerciseLogUseCases
                ),
                currentLogsViewModel = CurrentLogsViewModel(
                    savedStateHandle,
                    exerciseLogUseCases
                ),
                previousLogsViewModel = PreviousLogsViewModel(
                    savedStateHandle,
                    exerciseLogUseCases
                )
            )
        }
    }
}


@Preview("Log Top App Bar Preview")
@Composable
fun LogTopAppBarPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            LogTopAppBar(
                title = "Bench Press",
                onCloseScreen = { }
            )
        }
    }
}

@Preview("Log Item Preview")
@Composable
fun LogItemPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            ExerciseLogItem(
                exerciseLog = ExerciseLog(
                    1,
                    1,
                    8,
                    50.0,
                    120,
                    8,
                    50.0,
                    120,
                    LocalDate.now().toString()
                ),
                onDeleteClick = {}
            )
        }
    }
}

@Preview("Log Input Data Preview")
@Composable
fun LogInputDataPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            LogInputData(
                modifier = Modifier.padding(8.dp),
                label = "Goal",
                value = "8",
                onDecreaseValue = {  },
                onEnterValue = {},
                onIncreaseValue = {},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                )
            )
        }
    }
}

@Preview("Log Summary Item Preview")
@Composable
fun LogSummaryItemPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            LogSummaryItem(isVisible = true, volume = "1200", pause = "120")
        }
    }
}
