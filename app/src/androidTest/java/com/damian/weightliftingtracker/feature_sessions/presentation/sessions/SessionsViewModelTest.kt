package com.damian.weightliftingtracker.feature_sessions.presentation.sessions

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionsViewModelTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var viewModel: SessionsViewModel
    private val testSession = Session(1, 1, "a", "b")

    private lateinit var fakeRepository: FakeSessionRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        fakeRepository = FakeSessionRepository()
        savedStateHandle = SavedStateHandle()
        savedStateHandle[STATE_KEY_PLAN_ID] = 1

        viewModel = SessionsViewModel(
            SessionUseCases(
                getSessionsUseCase = GetSessionsUseCase(fakeRepository),
                getSessionByIdUseCase = GetSessionByIdUseCase(fakeRepository),
                addSessionUseCase = AddSessionUseCase(fakeRepository),
                updateSessionUseCase = UpdateSessionUseCase(fakeRepository),
                deleteSessionUseCase = DeleteSessionUseCase(fakeRepository)
            ),
            savedStateHandle,
            StringProvider(context)
        )
    }

    @Test
    fun onAddSessionEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnAdd)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(SessionsViewModel.SessionsUiEvent.NavToAddSession(1), event)
        }
    }


    @Test
    fun onMenuClickEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnSessionMenuClick(testSession))

            Assert.assertEquals(testSession, viewModel.uiState.value.selectedSession)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(
                SessionsViewModel.SessionsUiEvent.OpenContextMenu(testSession.sessionName), event
            )
        }
    }

    @Test
    fun onEditEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnSessionMenuClick(testSession))
            viewModel.onEvent(SessionsEvent.OnEdit)

            val event = viewModel.eventFlow.take(3).toList()

            Assert.assertEquals(
                SessionsViewModel.SessionsUiEvent.OpenContextMenu(testSession.sessionName),
                event[0]
            )
            Assert.assertEquals(SessionsViewModel.SessionsUiEvent.CloseContextMenu, event[1])
            Assert.assertEquals(
                SessionsViewModel.SessionsUiEvent.NavToEditSession(
                    testSession.planId,
                    testSession.id
                ), event[2]
            )
        }
    }

    @Test
    fun onDeleteEventTest() {
        Assert.assertFalse(viewModel.uiState.value.isDeleteDialogVisible)
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnDelete)
            val event = viewModel.eventFlow.first()
            Assert.assertEquals(SessionsViewModel.SessionsUiEvent.CloseContextMenu, event)
        }
        Assert.assertTrue(viewModel.uiState.value.isDeleteDialogVisible)
    }

    @Test
    fun onConfirmDeletionTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnSessionMenuClick(testSession))
            viewModel.onEvent(SessionsEvent.OnDelete)
            viewModel.onEvent(SessionsEvent.OnConfirmDeletion)

            viewModel.eventFlow.take(3).toList()

            Assert.assertFalse(viewModel.uiState.value.isDeleteDialogVisible)
            Assert.assertNull(viewModel.uiState.value.selectedSession)

        }
    }

    @Test
    fun onDismissDialogTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnSessionMenuClick(testSession))
            viewModel.onEvent(SessionsEvent.OnDismissDialog)

            Assert.assertFalse(viewModel.uiState.value.isDeleteDialogVisible)
            Assert.assertNull(viewModel.uiState.value.selectedSession)
        }
    }

    @Test
    fun onClickTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(SessionsEvent.OnSessionClick(testSession))
            val event = viewModel.eventFlow.first()

            Assert.assertEquals(
                event,
                SessionsViewModel.SessionsUiEvent.NavToDetails(
                    testSession.id,
                    testSession.sessionName
                )
            )
        }
    }
}