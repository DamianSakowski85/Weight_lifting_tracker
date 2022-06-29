package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker

import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.feature_exercise_history.data.repository.FakeExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.CalculateVolumeUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.ExerciseHistoryUseCases
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.GetCalendarConstrainUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.LoadLogUseCase
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class ExerciseHistoryViewModelTest {

    private lateinit var viewModel: ExerciseHistoryViewModel
    private val testExerciseLog = ExerciseLog(
        id = 1,
        exerciseId = 1,
        8,
        55.0,
        180,
        8,
        55.0,
        180,
        LocalDate.now().toString()
    )
    private val secondTestExerciseLog = ExerciseLog(
        id = 1,
        exerciseId = 2,
        8,
        55.0,
        180,
        6,
        55.0,
        100,
        LocalDate.now().minusDays(1).toString()
    )

    private lateinit var fakeRepo: FakeExerciseHistoryRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup(){
        fakeRepo = FakeExerciseHistoryRepository()
        fakeRepo.addExerciseLogForTest(testExerciseLog)
        fakeRepo.addExerciseLogForTest(secondTestExerciseLog)

        savedStateHandle = SavedStateHandle()
        viewModel = ExerciseHistoryViewModel(
            savedStateHandle,
            ExerciseHistoryUseCases(
                loadLogUseCase = LoadLogUseCase(fakeRepo),
                calculateVolumeUseCase = CalculateVolumeUseCase(),
                getCalendarConstrainUseCase = GetCalendarConstrainUseCase(fakeRepo)
            )
        )
    }

    @Test
    fun onBackPressedEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseHistoryEvent.OnBackPressed)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(ExerciseHistoryViewModel.UiEvent.NavBack,event)
        }
    }

    @Test
    fun onShowDatePickerEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseHistoryEvent.OnPickDate)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(ExerciseHistoryViewModel.UiEvent.ShowDatePicker,event)
        }
    }

    @Test
    fun onPickCurrentDateEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(ExerciseHistoryEvent.OnDateClick(LocalDate.now().toString()))
        }
        Assert.assertTrue(viewModel.exerciseHistoryState.value.logs.size == 1)
        Assert.assertTrue(viewModel.exerciseHistoryState.value.logs.first().exerciseId == 1)
        Assert.assertEquals(LocalDate.now().toString(),viewModel.exerciseHistoryState.value.date)
        Assert.assertFalse(viewModel.exerciseHistoryState.value.isLogEmpty)
        Assert.assertEquals("440.0", viewModel.exerciseHistoryState.value.weightVolume)
        Assert.assertEquals("180",viewModel.exerciseHistoryState.value.pauseVolume)
    }
    @Test
    fun onPickPreviousDateEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(
                ExerciseHistoryEvent.OnDateClick(
                    LocalDate.now().minusDays(1).toString()))
        }
        Assert.assertTrue(viewModel.exerciseHistoryState.value.logs.size == 1)
        Assert.assertTrue(viewModel.exerciseHistoryState.value.logs.first().exerciseId == 2)
        Assert.assertEquals(LocalDate.now().minusDays(1).toString(),viewModel.exerciseHistoryState.value.date)
        Assert.assertFalse(viewModel.exerciseHistoryState.value.isLogEmpty)
        Assert.assertEquals("330.0", viewModel.exerciseHistoryState.value.weightVolume)
        Assert.assertEquals("100",viewModel.exerciseHistoryState.value.pauseVolume)
    }
}