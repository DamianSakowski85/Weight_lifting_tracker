package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.charts

import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository.FakeExerciseVolumeRepo
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case.GetVolumeListUseCase
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class ChartsViewModelTest {

    private lateinit var viewModel: VolumeChartsViewModel
    private lateinit var fakeRepo: FakeExerciseVolumeRepo
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val testExerciseLog = ExerciseLog(
        id = 1,
        exerciseId = 1,
        8,
        55.0,
        180,
        8,
        55.0,
        180,
        LocalDate.now().minusDays(1).toString()
    )
    private val secondTestExerciseLog = ExerciseLog(
        id = 2,
        exerciseId = 1,
        8,
        55.0,
        180,
        6,
        55.0,
        100,
        LocalDate.now().toString()
    )

    @Before
    fun setup(){
        fakeRepo = FakeExerciseVolumeRepo()
        fakeRepo.addExerciseLogForTest(testExerciseLog)
        fakeRepo.addExerciseLogForTest(secondTestExerciseLog)

        savedStateHandle = SavedStateHandle()

        savedStateHandle["exerciseId"] = 1
        savedStateHandle["exerciseName"] = "Bench Press"


    }

    @Test
    fun initTest() {
        testCoroutineRule.runBlockingTest {
            viewModel = VolumeChartsViewModel(
                savedStateHandle,
                GetVolumeListUseCase(fakeRepo)
            )
        }
        Assert.assertEquals("Bench Press",viewModel.volumeChartsState.value.exerciseName)
        Assert.assertTrue(viewModel.volumeChartsState.value.showVolumeChart)
        Assert.assertTrue(viewModel.volumeChartsState.value.showPauseChart)
        Assert.assertFalse(viewModel.volumeChartsState.value.isBarDataEmpty)

        Assert.assertEquals(3,viewModel.volumeChartsState.value.volumeTest?.size)
    }

    @Test
    fun closeEventTest(){
        testCoroutineRule.runBlockingTest {
            viewModel = VolumeChartsViewModel(
                savedStateHandle,
                GetVolumeListUseCase(fakeRepo)
            )
            viewModel.onEvent(VolumeChartsEvent.OnBackPressed)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(VolumeChartsViewModel.UiEvent.PopBackStack,event)
        }
    }
}