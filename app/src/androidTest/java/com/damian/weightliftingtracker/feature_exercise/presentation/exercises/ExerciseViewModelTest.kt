package com.damian.weightliftingtracker.feature_exercise.presentation.exercises

import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.use_case.*
import com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session.STATE_KEY_SESSION_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExerciseViewModelTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var viewModel: ExerciseViewModel
    private val testExercise = Exercise(1, 1, "a", "b")

    private lateinit var fakeRepository: FakeExerciseRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeExerciseRepository()
        savedStateHandle = SavedStateHandle()
        savedStateHandle[STATE_KEY_SESSION_ID] = 1

        viewModel = ExerciseViewModel(
            savedStateHandle,
            exerciseUseCases = ExerciseUseCases(
                getExerciseByIdUseCase = GetExerciseByIdUseCase(fakeRepository),
                addExerciseUseCase = AddExerciseUseCase(fakeRepository),
                updateExerciseUseCase = UpdateExerciseUseCase(fakeRepository),
                getExercisesUseCase = GetExercisesUseCase(fakeRepository),
                clearExerciseDataUseCase = ClearExerciseDataUseCase(fakeRepository),
                deleteExerciseUseCase = DeleteExerciseUseCase(fakeRepository)
            ),
            StringProvider(context)
        )
    }

    @Test
    fun onAddSessionEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnAdd)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(ExerciseViewModel.UiEvent.NavToAddExercise(1), event)
        }
    }

    @Test
    fun onBackPressedEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnBackPressed)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(ExerciseViewModel.UiEvent.PopBackStack,event)
        }
    }

    @Test
    fun onConfirmDeletionEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnDelete)
            viewModel.onEvent(ExerciseEvent.OnConfirmDeletion)

            viewModel.eventFlow.take(3).toList()

            Assert.assertFalse(viewModel.exerciseState.value.isDeleteDialogVisible)
            Assert.assertNull(viewModel.exerciseState.value.selectedExercise)
        }
    }

    @Test
    fun onMenuClickTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))

            val  event = viewModel.eventFlow.first()

            Assert.assertEquals(
                ExerciseViewModel.UiEvent.OpenContextMenu(testExercise.exerciseName),
            event)

            Assert.assertNotNull(viewModel.exerciseState.value.selectedExercise)
        }
    }

    @Test
    fun onDismissDeleteDialogTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnDelete)

            Assert.assertTrue(viewModel.exerciseState.value.isDeleteDialogVisible)

            viewModel.onEvent(ExerciseEvent.OnConfirmDeletion)

            viewModel.eventFlow.take(3).toList()

            Assert.assertFalse(viewModel.exerciseState.value.isDeleteDialogVisible)
            Assert.assertNull(viewModel.exerciseState.value.selectedExercise)
        }
    }

    @Test
    fun onEditEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnEdit)

            val events = viewModel.eventFlow.take(3).toList()

            Assert.assertEquals(
                ExerciseViewModel.UiEvent.OpenContextMenu(testExercise.exerciseName),
                events[0]
            )
            Assert.assertEquals(ExerciseViewModel.UiEvent.CloseContextMenu, events[1])
            Assert.assertEquals(
                ExerciseViewModel.UiEvent.NavToEditExercise(
                    testExercise.sessionId,
                    testExercise.id
                ), events[2]
            )
        }
    }

    @Test
    fun onDeleteEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnDelete)

            viewModel.eventFlow.take(2).toList()

            Assert.assertTrue(viewModel.exerciseState.value.isDeleteDialogVisible)
            Assert.assertNotNull(viewModel.exerciseState.value.selectedExercise)
        }
    }

    @Test
    fun onShowChartsEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnShowBarCharts)

            val events = viewModel.eventFlow.take(3).toList()

            Assert.assertEquals(ExerciseViewModel.UiEvent.NavToLineCharts(testExercise),events[2])
        }
    }

    @Test
    fun onShowDatePickerEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnShowDatePicker)

            val events = viewModel.eventFlow.take(3).toList()

            Assert.assertEquals(
                ExerciseViewModel.UiEvent.NavToDatePicker(testExercise.id,testExercise.exerciseName),
                events[2])
        }
    }

    @Test
    fun onExerciseClickEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnExerciseClick(testExercise))

            Assert.assertNotNull(viewModel.exerciseState.value.selectedExercise)

            val event = viewModel.eventFlow.first()

            Assert.assertEquals(ExerciseViewModel.UiEvent.NavToExerciseLog(testExercise.id,testExercise.exerciseName),
            event)
        }
    }

    @Test
    fun onClearExerciseDataEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnClearExerciseData)
            Assert.assertTrue(viewModel.exerciseState.value.isClearHistoryDialogVisible)
        }
    }

    @Test
    fun onConfirmDataClearanceEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnClearExerciseData)

            Assert.assertTrue(viewModel.exerciseState.value.isClearHistoryDialogVisible)

            viewModel.onEvent(ExerciseEvent.OnConfirmDataClearance)

            //viewModel.eventFlow.take(3).toList()


        }
        Assert.assertFalse(viewModel.exerciseState.value.isClearHistoryDialogVisible)
        Assert.assertNull(viewModel.exerciseState.value.selectedExercise)
    }

    @Test
    fun onDismissDataClearanceEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseEvent.OnMenuClick(testExercise))
            viewModel.onEvent(ExerciseEvent.OnClearExerciseData)

            Assert.assertTrue(viewModel.exerciseState.value.isClearHistoryDialogVisible)

            viewModel.onEvent(ExerciseEvent.OnDismissDataClearance)

            Assert.assertFalse(viewModel.exerciseState.value.isClearHistoryDialogVisible)
            Assert.assertNull(viewModel.exerciseState.value.selectedExercise)
        }
    }
}