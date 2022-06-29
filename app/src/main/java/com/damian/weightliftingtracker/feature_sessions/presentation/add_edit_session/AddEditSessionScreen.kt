package com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.*
import com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session.components.AddEditSessionTopAppBar
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddEditSessionScreen(
    closeScreen: () -> Unit,
    viewModelImpl: AddEditSessionViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val addEditSessionState = viewModelImpl.uiState.value
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModelImpl.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditSessionViewModel.AddEditSessionUiEvent.NavBack -> {
                    keyboardController?.hide()
                    closeScreen()
                }
                is AddEditSessionViewModel.AddEditSessionUiEvent.ShowMessage -> {
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
            AddEditSessionTopAppBar(
                title = stringResource(R.string.session),
                onCloseClick = {
                    viewModelImpl.onEvent(AddEditSessionEvent.OnCloseClick)
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(id = R.string.save_session)
                    )
                },
                onClick = {
                    viewModelImpl.onEvent(AddEditSessionEvent.OnSaveSession)
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
                addEditSessionState.name,
                addEditSessionState.description,
                onEnterName = { enteredName ->
                    viewModelImpl.onEvent(AddEditSessionEvent.OnEnterName(enteredName))
                },
                onEnterDescription = { enteredDescription ->
                    viewModelImpl.onEvent(
                        AddEditSessionEvent.OnEnterDescription(
                            enteredDescription
                        )
                    )
                }
            )
        }
    }
}

@Preview("Add Edit Session Screen Preview")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun AddEditSessionScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            AddEditSessionScreen(
                closeScreen = {},
                viewModelImpl = AddEditSessionViewModel(
                    SessionUseCases(
                        getSessionsUseCase = GetSessionsUseCase(FakeSessionRepository()),
                        getSessionByIdUseCase = GetSessionByIdUseCase(FakeSessionRepository()),
                        addSessionUseCase = AddSessionUseCase(FakeSessionRepository()),
                        updateSessionUseCase = UpdateSessionUseCase(FakeSessionRepository()),
                        deleteSessionUseCase = DeleteSessionUseCase(FakeSessionRepository())
                    ),
                    SavedStateHandle(),
                    StringProvider(context)
                )
            )
        }
    }
}