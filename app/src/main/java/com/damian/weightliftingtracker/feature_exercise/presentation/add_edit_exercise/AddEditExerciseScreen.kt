package com.damian.weightliftingtracker.feature_exercise.presentation.add_edit_exercise

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.presentation.components.TextFieldsCard
import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.use_case.*
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddEditExerciseScreen(
    closeScreen: () -> Unit,
    viewModel: AddEditExerciseViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val addEditExerciseState = viewModel.addEditExerciseState.value
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditExerciseViewModel.UiEvent.NavBack -> {
                    keyboardController?.hide()
                    closeScreen()
                }
                is AddEditExerciseViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(
                        context, event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.exercise),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddEditExerciseEvent.OnCloseClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(id = R.string.save_exercise)
                    )
                },
                onClick = {
                    keyboardController?.hide()
                    viewModel.onEvent(AddEditExerciseEvent.OnSaveSession)
                },
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxSize()
        ) {
            TextFieldsCard(
                addEditExerciseState.name,
                addEditExerciseState.description,
                onEnterName = {
                    viewModel.onEvent(AddEditExerciseEvent.OnEnterName(it))
                },
                onEnterDescription = {
                    viewModel.onEvent(AddEditExerciseEvent.OnEnterDescription(it))
                }
            )
        }
    }
}

@Preview("Add Edit Exercise Screen Preview")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun AddEditExerciseScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            val fakeExerciseRepository = FakeExerciseRepository()
            AddEditExerciseScreen(
                closeScreen = {},
                viewModel = AddEditExerciseViewModel(
                    SavedStateHandle(),
                    StringProvider(context),
                    ExerciseUseCases(
                        getExerciseByIdUseCase = GetExerciseByIdUseCase(fakeExerciseRepository),
                        addExerciseUseCase = AddExerciseUseCase(fakeExerciseRepository),
                        updateExerciseUseCase = UpdateExerciseUseCase(fakeExerciseRepository),
                        getExercisesUseCase = GetExercisesUseCase(fakeExerciseRepository),
                        clearExerciseDataUseCase = ClearExerciseDataUseCase(fakeExerciseRepository),
                        deleteExerciseUseCase = DeleteExerciseUseCase(fakeExerciseRepository)
                    )
                )
            )
        }
    }
}