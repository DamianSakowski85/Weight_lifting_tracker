package com.damian.weightliftingtracker.feature_plans.presentation

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
class PlanEndToEndTest {

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
    fun openAndCloseSortOrderTest() = runTest(testDispatcher) {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(
            context.getString(R.string.sort)
        ).performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(
            context.getString(R.string.sort)
        ).performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
    }

    @Test
    fun openAndSortByNameAscTest() = runTest(testDispatcher) {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(
            context.getString(R.string.sort)
        ).performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .assertIsSelected()
    }

    @Test
    fun openAndSortByNameDescTest() = runTest(testDispatcher) {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(
            context.getString(R.string.sort)
        ).performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .assertIsSelected()
    }

    @Test
    fun openAndSortByDateAscTest() = runTest(testDispatcher) {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(
            context.getString(R.string.sort)
        ).performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .assertIsSelected()
    }

    @Test
    fun openAndSortByDateDescTest() = runTest(testDispatcher) {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(
            context.getString(R.string.sort)
        ).performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .assertIsSelected()
    }

    @Test
    fun saveAndUpdatePlanTest() = runTest(testDispatcher) {
        composeRule.onNodeWithContentDescription(context.getString(R.string.create_plan))
            .performClick()
        composeRule.waitForIdle()
        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.DESC_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription(context.getString(R.string.save_plan))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-content").assertIsDisplayed()

        composeRule.onNode(hasTestTag("test-title")).performClick()
        composeRule.onNode(hasTestTag("test-title")).assertHasClickAction()
        composeRule.onNode(hasTestTag("EDIT_DELETE_MENU_SECTION")).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.edit))
            .performClick()
        composeRule.waitForIdle()
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

        composeRule.onNodeWithContentDescription(context.getString(R.string.save_plan))
            .performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("_update_name").assertIsDisplayed()
        composeRule.onNodeWithText("_update_desc").assertIsDisplayed()
    }

    @Test
    fun saveAndDeletePlanTest() = runTest(testDispatcher) {
        composeRule.onNodeWithContentDescription(context.getString(R.string.create_plan))
            .performClick()
        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.DESC_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription(context.getString(R.string.save_plan))
            .performClick()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-content").assertIsDisplayed()

        composeRule.onNode(hasTestTag("test-title")).performClick()
        composeRule.onNode(hasTestTag("test-title")).assertHasClickAction()
        composeRule.onNode(hasTestTag("EDIT_DELETE_MENU_SECTION")).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.delete))
            .performClick()

        composeRule.onNodeWithText(context.getString(R.string.delete_selected_plan))
            .assertIsDisplayed()

        composeRule.onNodeWithText(context.getString(R.string.yes)).performClick()

        composeRule.onNodeWithText(context.getString(R.string.delete_selected_plan))
            .assertDoesNotExist()
        composeRule.onNodeWithText("test-title").assertDoesNotExist()
        composeRule.onNodeWithText("test-content").assertDoesNotExist()
    }
}