package com.damian.weightliftingtracker.feature_exercise_log.presentation.log

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
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.utli.TestTags.PAUSE_ACHIEVED
import com.damian.weightliftingtracker.core.utli.TestTags.PAUSE_GOAL
import com.damian.weightliftingtracker.core.utli.TestTags.REPS_ACHIEVED
import com.damian.weightliftingtracker.core.utli.TestTags.REPS_GOAL
import com.damian.weightliftingtracker.core.utli.TestTags.WEIGHT_ACHIEVED
import com.damian.weightliftingtracker.core.utli.TestTags.WEIGHT_GOAL
import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.*
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.AddLogViewModel
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.STATE_KEY_EXERCISE_ID
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.STATE_KEY_EXERCISE_NAME
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.current_logs.CurrentLogsViewModel
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.previous_logs.PreviousLogsViewModel
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.runBlocking
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
class ExerciseLogScreenTest {

    @get: Rule
    val composeRule = createComposeRule()

    val context: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private var isScreenClosed = false

    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo
    private lateinit var addLogViewModel: AddLogViewModel
    private lateinit var currentLogsViewModel: CurrentLogsViewModel
    private lateinit var previousLogsViewModel: PreviousLogsViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringProvider: StringProvider
    private lateinit var exerciseLogUseCases: ExerciseLogUseCases

    private val previousLog = ExerciseLog(
        1,
        1,
        8,
        50.0,
        120,
        8,
        50.0,
        120,
        LocalDate.now().minusDays(1).toString()
    )

    private val currentLog = ExerciseLog(
        2,
        1,
        8,
        50.0,
        120,
        8,
        50.0,
        120,
        LocalDate.now().toString()
    )


    @Before
    fun setup() = runBlocking {
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        savedStateHandle = SavedStateHandle()

        savedStateHandle[STATE_KEY_EXERCISE_ID] = 1
        savedStateHandle[STATE_KEY_EXERCISE_NAME] = "Bench Press"

        fakeExerciseLogRepo.insert(previousLog)
        fakeExerciseLogRepo.insert(currentLog)

        stringProvider = StringProvider(context)
        exerciseLogUseCases = ExerciseLogUseCases(
            addExerciseLogUseCase = AddExerciseLogUseCase(fakeExerciseLogRepo),
            calculateVolumeUseCase = CalculateVolumeUseCase(),
            currentExerciseLogsUseCase = CurrentExerciseLogsUseCase(fakeExerciseLogRepo),
            decreasePauseValueUseCase = DecreasePauseValueUseCase(),
            decreaseRepsValueUseCase = DecreaseRepsValueUseCase(),
            decreaseWeightValueUseCase = DecreaseWeightValueUseCase(),
            deleteSelectedExerciseLogUseCase = DeleteSelectedExerciseLogUseCase(fakeExerciseLogRepo),
            getLastSetUseCase = GetLastSetUseCase(fakeExerciseLogRepo),
            increasePauseValueUseCase = IncreasePauseValueUseCase(),
            increaseRepsValueUseCase = IncreaseRepsValueUseCase(),
            increaseWeightValueUseCase = IncreaseWeightValueUseCase(),
            previousExerciseLogsUseCase = PreviousExerciseLogsUseCase(fakeExerciseLogRepo)
        )

        addLogViewModel = AddLogViewModel(
            savedStateHandle, stringProvider, exerciseLogUseCases
        )
        currentLogsViewModel = CurrentLogsViewModel(
            savedStateHandle, exerciseLogUseCases
        )
        previousLogsViewModel = PreviousLogsViewModel(
            savedStateHandle, exerciseLogUseCases
        )

        composeRule.setContent {
            Surface {
                WeightLiftingTrackerTheme {
                    ExerciseLogScreen(
                        closeScreen = { isScreenClosed = true },
                        addLogViewModel = addLogViewModel,
                        currentLogsViewModel = currentLogsViewModel,
                        previousLogsViewModel = previousLogsViewModel
                    )
                }
            }
        }
    }

    @Test
    fun initAndCloseScreenTest(){
        composeRule.onNodeWithText(context.getString(R.string.no_current_session)).assertDoesNotExist()
        composeRule.onNodeWithText(context.getString(R.string.no_current_session)).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(context.getString(R.string.close)).performClick()
        Assert.assertTrue(isScreenClosed)
    }

    @Test
    fun logInputWeightGoalTest() = runTest(testDispatcher){
        composeRule.onNode(hasTestTag(WEIGHT_GOAL)).onChildAt(0).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("49.5").assertIsDisplayed()

        composeRule.onNode(hasTestTag(WEIGHT_GOAL)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("50.0").assertIsDisplayed()

        composeRule.onNode(hasTestTag(WEIGHT_GOAL)).onChildAt(1).performTextReplacement("60.0")
        composeRule.waitForIdle()
        composeRule.onNodeWithText("60.0").assertIsDisplayed()
    }

    @Test
    fun logInputRepsGoalTest() = runTest(testDispatcher){
        composeRule.onNode(hasTestTag(REPS_GOAL)).onChildAt(0).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("7").assertIsDisplayed()

        composeRule.onNode(hasTestTag(REPS_GOAL)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("8").assertIsDisplayed()

        composeRule.onNode(hasTestTag(REPS_GOAL)).onChildAt(1).performTextReplacement("10")
        composeRule.waitForIdle()
        composeRule.onNodeWithText("10").assertIsDisplayed()
    }

    @Test
    fun logInputPauseGoalTest() = runTest(testDispatcher){
        composeRule.onNode(hasTestTag(PAUSE_GOAL)).onChildAt(0).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("115").assertIsDisplayed()

        composeRule.onNode(hasTestTag(PAUSE_GOAL)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("120").assertIsDisplayed()

        composeRule.onNode(hasTestTag(PAUSE_GOAL)).onChildAt(1).performTextReplacement("180")
        composeRule.waitForIdle()
        composeRule.onNodeWithText("180").assertIsDisplayed()
    }

    @Test
    fun logInputWeightAchievedTest() = runTest(testDispatcher) {
        composeRule.onNode(hasTestTag(WEIGHT_ACHIEVED)).onChildAt(1).performTextReplacement("100.0")
        composeRule.waitForIdle()
        composeRule.onNodeWithText("100.0").assertIsDisplayed()

        composeRule.onNode(hasTestTag(WEIGHT_ACHIEVED)).onChildAt(0).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("99.5").assertIsDisplayed()

        composeRule.onNode(hasTestTag(WEIGHT_ACHIEVED)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("100.0").assertIsDisplayed()
    }

    @Test
    fun logInputRepsAchievedTest() = runTest(testDispatcher) {
        composeRule.onNode(hasTestTag(REPS_ACHIEVED)).onChildAt(1).performTextReplacement("12")
        composeRule.waitForIdle()
        composeRule.onNodeWithText("12").assertIsDisplayed()

        composeRule.onNode(hasTestTag(REPS_ACHIEVED)).onChildAt(0).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("11").assertIsDisplayed()

        composeRule.onNode(hasTestTag(REPS_ACHIEVED)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("12").assertIsDisplayed()
    }

    @Test
    fun logInputPauseAchievedTest() = runTest(testDispatcher) {
        composeRule.onNode(hasTestTag(PAUSE_ACHIEVED)).onChildAt(1).performTextReplacement("180")
        composeRule.waitForIdle()
        composeRule.onNodeWithText("180").assertIsDisplayed()

        composeRule.onNode(hasTestTag(PAUSE_ACHIEVED)).onChildAt(0).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("175").assertIsDisplayed()

        composeRule.onNode(hasTestTag(PAUSE_ACHIEVED)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("180").assertIsDisplayed()
    }

    @Test
    fun saveLogTest() = runTest(testDispatcher) {
        composeRule.onNode(hasTestTag(WEIGHT_GOAL)).onChildAt(1).performTextReplacement("100.0")
        composeRule.onNode(hasTestTag(REPS_GOAL)).onChildAt(1).performTextReplacement("10")
        composeRule.onNode(hasTestTag(PAUSE_GOAL)).onChildAt(1).performTextReplacement("200")
        composeRule.waitForIdle()

        composeRule.onNodeWithText(context.getString(R.string.complete_set)).performClick()
        composeRule.waitForIdle()

        composeRule.onNode(hasTestTag(WEIGHT_ACHIEVED)).onChildAt(1).assertTextContains("100.0")
        composeRule.onNode(hasTestTag(REPS_ACHIEVED)).onChildAt(1).assertTextContains("10")
        composeRule.onNode(hasTestTag(PAUSE_ACHIEVED)).onChildAt(1).assertTextContains("200")
    }
}