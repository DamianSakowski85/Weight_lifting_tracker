package com.damian.weightliftingtracker.feature_sessions.presentation.sessions

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
import com.damian.weightliftingtracker.core.presentation.components.EditDeleteMenu
import com.damian.weightliftingtracker.core.presentation.components.EmptyListLabel
import com.damian.weightliftingtracker.core.presentation.components.Header
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.*
import com.damian.weightliftingtracker.feature_sessions.presentation.sessions.components.SessionItem
import com.damian.weightliftingtracker.feature_sessions.presentation.sessions.components.SessionsTopAppBar
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SessionScreen(
    closeScreen: () -> Unit,
    navToAddSessionScreen: (planId: Int) -> Unit,
    navToEditSessionScreen: (planId: Int, sessionId: Int) -> Unit,
    navToDetails: (sessionId: Int, sessionName: String) -> Unit,
    viewModel: SessionsViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val sessionState = viewModel.uiState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SessionsViewModel.SessionsUiEvent.PopBackStack -> {
                    closeScreen()
                }
                is SessionsViewModel.SessionsUiEvent.ShowMessage -> {
                    Toast.makeText(
                        context, event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is SessionsViewModel.SessionsUiEvent.NavToAddSession -> {
                    navToAddSessionScreen(event.planId)
                }
                is SessionsViewModel.SessionsUiEvent.NavToEditSession -> {
                    navToEditSessionScreen(event.planId, event.sessionId)
                }
                is SessionsViewModel.SessionsUiEvent.NavToDetails -> {
                    navToDetails(event.sessionId, event.sessionName)
                }
                is SessionsViewModel.SessionsUiEvent.OpenContextMenu -> {
                    scope.launch {
                        modalBottomSheetState.show()
                    }
                }
                is SessionsViewModel.SessionsUiEvent.CloseContextMenu -> {
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            }
        }
    }

    DeleteDialog(
        title = sessionState.selectedSession?.sessionName ?: "",
        message = stringResource(id = R.string.delete_selected_session),
        isVisible = sessionState.isDeleteDialogVisible,
        onConfirmClick = {
            viewModel.onEvent(SessionsEvent.OnConfirmDeletion)
        },
        onDismissClick = {
            viewModel.onEvent(SessionsEvent.OnDismissDialog)
        },
        onDismissRequest = {
            viewModel.onEvent(SessionsEvent.OnDismissDialog)
        }
    )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            EditDeleteMenu(
                title = sessionState.selectedSession?.sessionName,

                onUpdateClick = {
                    viewModel.onEvent(SessionsEvent.OnEdit)
                },
                onDeleteClick = {
                    viewModel.onEvent(SessionsEvent.OnDelete)
                },
                modifier = Modifier.testTag(TestTags.EDIT_DELETE_MENU_SECTION)
            )
        }
    ) {
        Scaffold(
            topBar = {
                SessionsTopAppBar(
                    title = viewModel.uiState.value.planName,
                    onBackArrowClick = { viewModel.onEvent(SessionsEvent.OnBackPressed) }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.create_session)
                        )
                    },
                    onClick = {
                        viewModel.onEvent(SessionsEvent.OnAdd)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
            },
            scaffoldState = scaffoldState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(it)
            ) {
                Header(stringResource(R.string.training_sessions))

                EmptyListLabel(
                    isListEmpty = sessionState.isEmptyListLabelVisible,
                    text = stringResource(id = R.string.you_have_no_sessions),
                    animationDuration = 0,
                    modifier = Modifier.fillMaxSize()
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(sessionState.sessions) { session ->
                        SessionItem(
                            session = session,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(SessionsEvent.OnSessionClick(session))
                                },
                            onMenuClick = {
                                viewModel.onEvent(SessionsEvent.OnSessionMenuClick(session))
                            }
                        )
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

@Preview("Session Screen Preview")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun SessionScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            SessionScreen(
                closeScreen = { },
                navToAddSessionScreen = { },
                navToEditSessionScreen = { _, _ -> },
                navToDetails = { _, _ -> },
                viewModel = SessionsViewModel(
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