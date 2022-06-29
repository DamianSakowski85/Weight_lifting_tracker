package com.damian.weightliftingtracker.feature_exercise.presentation

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
class ExercisesEndToEndTest {
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
    fun addAndEditExerciseTest() = runTest(testDispatcher){
        composeRule.onNodeWithText("Push Pull Legs 2").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Training A").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(context.getString(R.string.create_exercise))
            .performClick()
        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.DESC_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription(context.getString(R.string.save_exercise))
            .performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-content").assertIsDisplayed()

        composeRule.onNode(hasTestTag("test-title")).performClick()
        composeRule.onNode(hasTestTag("test-title")).assertHasClickAction()
        composeRule.onNode(hasTestTag(TestTags.EXERCISE_MENU_SECTION)).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.edit))
            .performClick()

        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextClearance()
        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextInput("_update_name")
        composeRule
            .onNodeWithTag(TestTags.DESC_TEXT_FIELD)
            .performTextClearance()
        composeRule
            .onNodeWithTag(TestTags.DESC_TEXT_FIELD)
            .performTextInput("_update_desc")

        composeRule.onNodeWithContentDescription(context.getString(R.string.save_exercise))
            .performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText("_update_name").assertIsDisplayed()
        composeRule.onNodeWithText("_update_desc").assertIsDisplayed()
    }

    @Test
    fun saveAndDeleteTest() = runTest(testDispatcher){
        composeRule.onNodeWithText("Push Pull Legs 2").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Training A").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(context.getString(R.string.create_exercise))
            .performClick()
        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.DESC_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription(context.getString(R.string.save_exercise))
            .performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-content").assertIsDisplayed()

        composeRule.onNode(hasTestTag("test-title")).performClick()
        composeRule.onNode(hasTestTag("test-title")).assertHasClickAction()

        composeRule.waitForIdle()

        composeRule.onNode(hasTestTag(TestTags.EXERCISE_MENU_SECTION)).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.delete))
            .performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText(context.getString(R.string.delete_selected_exercise))
            .assertIsDisplayed()

        composeRule.onNodeWithText(context.getString(R.string.yes)).performClick()

        composeRule.waitForIdle()


        composeRule.onNodeWithText(context.getString(R.string.delete_selected_exercise))
            .assertDoesNotExist()

        composeRule.waitForIdle()

        composeRule.onNodeWithText("test-title").assertDoesNotExist()
        composeRule.onNodeWithText("test-content").assertDoesNotExist()
    }
}