package com.damian.weightliftingtracker.feature_exercise.presentation.exercises

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.presentation.components.DeleteDialog
import com.damian.weightliftingtracker.core.presentation.components.EmptyListLabel
import com.damian.weightliftingtracker.core.presentation.components.Header
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.use_case.*
import com.damian.weightliftingtracker.feature_exercise.presentation.exercises.components.BottomMenuContent
import com.damian.weightliftingtracker.feature_exercise.presentation.exercises.components.ExerciseItem
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ExerciseScreen(
    closeScreen: () -> Unit,
    navToAddExerciseScreen: (sessionId: Int) -> Unit,
    navToEditExerciseScreen: (sessionId: Int, exerciseId: Int) -> Unit,
    navToExerciseLogScreen: (exerciseId: Int, exerciseName: String) -> Unit,
    navToChartsScreen: (exerciseId: Int, exerciseName: String) -> Unit,
    navToDatePickerScreen: (exerciseId: Int, exerciseName: String) -> Unit,
    viewModel: ExerciseViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ExerciseViewModel.UiEvent.PopBackStack -> {
                    closeScreen()
                }
                is ExerciseViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(
                        context, event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ExerciseViewModel.UiEvent.NavToAddExercise -> {
                    navToAddExerciseScreen(event.sessionId)
                }
                is ExerciseViewModel.UiEvent.NavToEditExercise -> {
                    navToEditExerciseScreen(event.sessionId, event.exerciseId)
                }
                is ExerciseViewModel.UiEvent.NavToExerciseLog -> {
                    navToExerciseLogScreen(event.exerciseId, event.exerciseName)
                }
                is ExerciseViewModel.UiEvent.NavToLineCharts -> {
                    navToChartsScreen(event.exercise.id, event.exercise.exerciseName)
                }
                is ExerciseViewModel.UiEvent.NavToDatePicker -> {
                    navToDatePickerScreen(event.exerciseId, event.exerciseName)
                }
                is ExerciseViewModel.UiEvent.OpenContextMenu -> {
                    scope.launch {
                        modalBottomSheetState.show()
                    }
                }
                is ExerciseViewModel.UiEvent.CloseContextMenu -> {
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            }
        }
    }

    DeleteDialog(
        title = viewModel.exerciseState.value.selectedExercise?.exerciseName ?: "",
        message = stringResource(id = R.string.delete_selected_exercise),
        isVisible = viewModel.exerciseState.value.isDeleteDialogVisible,
        onConfirmClick = {
            viewModel.onEvent(ExerciseEvent.OnConfirmDeletion)
        },
        onDismissClick = {
            viewModel.onEvent(ExerciseEvent.OnDismissDialog)
        },
        onDismissRequest = {
            viewModel.onEvent(ExerciseEvent.OnDismissDialog)
        }
    )

    DeleteDialog(
        title = viewModel.exerciseState.value.selectedExercise?.exerciseName ?: "",
        message = stringResource(id = R.string.clear_history),
        isVisible = viewModel.exerciseState.value.isClearHistoryDialogVisible,
        onConfirmClick = {
            viewModel.onEvent(ExerciseEvent.OnConfirmDataClearance)
        },
        onDismissClick = {
            viewModel.onEvent(ExerciseEvent.OnDismissDataClearance)
        },
        onDismissRequest = {
            viewModel.onEvent(ExerciseEvent.OnDismissDataClearance)
        }
    )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            BottomMenuContent(
                title = viewModel.exerciseState.value.selectedExercise?.exerciseName,
                onShowBarChartClick = {
                    viewModel.onEvent(ExerciseEvent.OnShowBarCharts)
                },
                onUpdateClick = {
                    viewModel.onEvent(ExerciseEvent.OnEdit)
                },
                onDeleteClick = {
                    viewModel.onEvent(ExerciseEvent.OnDelete)
                },
                onShowDatePickerClick = {
                    viewModel.onEvent(ExerciseEvent.OnShowDatePicker)
                },
                onClearDataClick = {
                    viewModel.onEvent(ExerciseEvent.OnClearExerciseData)
                },
                modifier = Modifier
                    .testTag(TestTags.EXERCISE_MENU_SECTION)
                    .padding(8.dp)
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = viewModel.exerciseState.value.sessionName
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ExerciseEvent.OnBackPressed)
                            }
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.nav_back),
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            stringResource(id = R.string.create_exercise)
                        )
                    },
                    onClick = {
                        viewModel.onEvent(ExerciseEvent.OnAdd)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            },
            scaffoldState = scaffoldState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(it)
            ) {
                Header(stringResource(R.string.exercises))

                EmptyListLabel(
                    isListEmpty = viewModel.exerciseState.value.isEmptyListLabelVisible,
                    text = stringResource(id = R.string.you_have_no_exercises),
                    animationDuration = 0,
                    modifier = Modifier.fillMaxSize()
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(viewModel.exerciseState.value.exercises) { exercise ->
                        ExerciseItem(
                            exercise = exercise,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(ExerciseEvent.OnExerciseClick(exercise))
                                },
                            onMenuClick = {
                                viewModel.onEvent(ExerciseEvent.OnMenuClick(exercise))
                            })
                        Divider(startIndent = 0.dp)
                    }
                    item {
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }
            }
        }
    }
}

@Preview("Exercise Screen Preview")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun ExerciseScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            val fakeExerciseRepository = FakeExerciseRepository()
            ExerciseScreen(
                closeScreen = { },
                navToAddExerciseScreen = {},
                navToEditExerciseScreen = { _, _ -> },
                navToExerciseLogScreen = { _, _ -> },
                navToChartsScreen = { _, _ -> },
                navToDatePickerScreen = { _, _ -> },
                viewModel = ExerciseViewModel(
                    SavedStateHandle(),
                    exerciseUseCases = ExerciseUseCases(
                        getExerciseByIdUseCase = GetExerciseByIdUseCase(fakeExerciseRepository),
                        addExerciseUseCase = AddExerciseUseCase(fakeExerciseRepository),
                        updateExerciseUseCase = UpdateExerciseUseCase(fakeExerciseRepository),
                        getExercisesUseCase = GetExercisesUseCase(fakeExerciseRepository),
                        clearExerciseDataUseCase = ClearExerciseDataUseCase(fakeExerciseRepository),
                        deleteExerciseUseCase = DeleteExerciseUseCase(fakeExerciseRepository)
                    ),
                    StringProvider(context)
                )
            )
        }
    }
}