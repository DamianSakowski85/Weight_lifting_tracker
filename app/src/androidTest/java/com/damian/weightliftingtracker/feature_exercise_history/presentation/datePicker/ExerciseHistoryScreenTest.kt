package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker


import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.feature_exercise_history.data.repository.FakeExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.CalculateVolumeUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.ExerciseHistoryUseCases
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.GetCalendarConstrainUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.LoadLogUseCase
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
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
class ExerciseHistoryScreenTest {

    @get: Rule
    val composeRule = createComposeRule()

    private lateinit var viewModel: ExerciseHistoryViewModel
    private lateinit var fakeRepo: FakeExerciseHistoryRepository

    val context : Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private var isScreenClosed = false

    @Before
    fun setUp() {
        fakeRepo = FakeExerciseHistoryRepository()
        (1..2).forEachIndexed { index, c ->
            fakeRepo.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().toString()
                )
            )
        }

        val savedStateHandle = SavedStateHandle()

        savedStateHandle[STATE_KEY_EXERCISE_ID] = 1
        savedStateHandle[STATE_KEY_EXERCISE_NAME] = "Bench Press"

        viewModel = ExerciseHistoryViewModel(
            savedStateHandle,
            ExerciseHistoryUseCases(
                loadLogUseCase = LoadLogUseCase(fakeRepo),
                calculateVolumeUseCase = CalculateVolumeUseCase(),
                getCalendarConstrainUseCase = GetCalendarConstrainUseCase(fakeRepo)
            )
        )

        composeRule.setContent {
            Surface {
                WeightLiftingTrackerTheme {
                    ExerciseDatePickerScreen(
                        closeScreen = { isScreenClosed = true},
                        viewModel = viewModel
                    )
                }
            }

        }
    }

    @Test
    fun displayLastLogTest() = runTest(testDispatcher) {
        val title = buildAnnotatedString {
            append("Bench Press")
            append(" • ")
            append(context.getString(R.string.history))
        }
        val displayedDate = buildAnnotatedString {
            append(context.getString(R.string.session_from))
            append(" ")
            append(LocalDate.now().toString())
        }.toString()

        composeRule.onNodeWithText(title.toString()).assertIsDisplayed()
        composeRule.onNodeWithText(displayedDate).assertIsDisplayed()
        composeRule.onNodeWithText("Set 1").assertIsDisplayed()
        composeRule.onNodeWithText("Set 2").assertIsDisplayed()

        composeRule.onNodeWithText(context.getString(R.string.select_session)).assertIsDisplayed()
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