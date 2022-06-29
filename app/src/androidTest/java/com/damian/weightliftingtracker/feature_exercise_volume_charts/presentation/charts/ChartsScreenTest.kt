package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.charts

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository.FakeExerciseVolumeRepo
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case.GetVolumeListUseCase
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class ChartsScreenTest {

    @get: Rule
    val composeRule = createComposeRule()

    private lateinit var viewModel: VolumeChartsViewModel
    private lateinit var fakeRepo: FakeExerciseVolumeRepo

    val context : Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private var isScreenClosed = false

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
    fun setup() {
        fakeRepo = FakeExerciseVolumeRepo()
        fakeRepo.addExerciseLogForTest(testExerciseLog)
        fakeRepo.addExerciseLogForTest(secondTestExerciseLog)

        val savedStateHandle = SavedStateHandle()

        savedStateHandle["exerciseId"] = 1
        savedStateHandle["exerciseName"] = "Bench Press"

        viewModel = VolumeChartsViewModel(
            savedStateHandle,
            GetVolumeListUseCase(fakeRepo)
        )

        composeRule.setContent {
            Surface {
                WeightLiftingTrackerTheme {
                    BarChartsVolumeScreen(
                        closeScreen = { isScreenClosed = true},
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    @Test
    fun displayTest(){
        composeRule.onNodeWithText(context.getString(R.string.volume_kg)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.pause_time_sec))

        composeRule.onNode(hasTestTag(TestTags.VOLUME_TEST_TAG)).assertIsDisplayed()
        composeRule.onNode(hasTestTag(TestTags.VOLUME_PAUSE_TEST_TAG)).assertIsDisplayed()
    }

    @Test
    fun closeEventTest() = runTest(testDispatcher) {
        composeRule.onNodeWithContentDescription(context.getString(R.string.close))
            .assertHasClickAction()
        composeRule.onNodeWithContentDescription(context.getString(R.string.close))
            .performClick()

        Assert.assertTrue(isScreenClosed)
    }
}