package com.damian.weightliftingtracker.feature_exercise_log.presentation

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.buildAnnotatedString
import androidx.test.core.app.ApplicationProvider
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.presentation.MainActivity
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ExerciseLogScreenEndToEndTest {

    @get: Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()


    val context: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun displayTest() = runTest(testDispatcher) {
        composeRule.onNodeWithText("Push Pull Legs 2").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Training A").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Bench Press").performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText(context.getString(R.string.no_current_session)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.no_previous_session)).assertDoesNotExist()

        composeRule.onNode(hasTestTag(TestTags.WEIGHT_GOAL)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNode(hasTestTag(TestTags.REPS_GOAL)).onChildAt(2).performClick()
        composeRule.waitForIdle()
        composeRule.onNode(hasTestTag(TestTags.PAUSE_GOAL)).onChildAt(2).performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText(context.getString(R.string.complete_set)).performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText(context.getString(R.string.no_current_session)).assertDoesNotExist()
        val displayedVolume = buildAnnotatedString {
            append(context.getString(R.string.volume))
            append(" : ")
            append("555.0")
            append(" ")
            append(context.getString(R.string.kg))
        }.toString()

        val displayedPauseVolume = buildAnnotatedString {
            append(context.getString(R.string.pause_time))
            append(" : ")
            append("185")
            append(" ")
            append(context.getString(R.string.sec))
        }.toString()


        composeRule.onNodeWithText(displayedVolume).assertIsDisplayed()
        composeRule.onNodeWithText(displayedPauseVolume).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.delete_selected_set)).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Yes").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText(context.getString(R.string.no_current_session)).assertIsDisplayed()
    }
}