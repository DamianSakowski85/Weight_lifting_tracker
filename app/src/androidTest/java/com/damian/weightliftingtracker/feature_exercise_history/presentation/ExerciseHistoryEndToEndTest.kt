package com.damian.weightliftingtracker.feature_exercise_history.presentation

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.buildAnnotatedString
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.text.StringBuilder

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ExerciseHistoryEndToEndTest {
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
    fun datePickerTest() = runTest(testDispatcher){
        composeRule.onNodeWithText("Push Pull Legs 2").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Training A").performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText("Bench Press").assertIsDisplayed()
        composeRule.onNode(hasTestTag("Bench Press")).performClick()
        composeRule.waitForIdle()

        composeRule.onNode(hasTestTag(TestTags.EXERCISE_MENU_SECTION)).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.show_all_sessions))
            .performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText(context.getString(R.string.select_session)).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.select_session)).performClick()
        composeRule.waitForIdle()

        val today = LocalDate.now()
        val testDay = LocalDate.now().minusDays(2)
        val day = testDay.dayOfWeek.name.substring(0,3)
        val dayBuilder = StringBuilder()
        day.forEach {
            if (dayBuilder.isEmpty()){
                dayBuilder.append(it)
            }else{
                dayBuilder.append(it.lowercaseChar())
            }
        }

        if (today.dayOfMonth == 1){
            onView(withResourceName("month_navigation_previous")).perform(click())
            composeRule.waitForIdle()
        }
        val dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd")
        val formattedDate = testDay.format(dateTimeFormatter)

        if (formattedDate[4] == '0'){
            val finalDate = formattedDate.replaceFirst("0","")
            val finalDateDesc = dayBuilder
                .append(",")
                .append(" ")
                .append(finalDate).toString()

            onView(withContentDescription(finalDateDesc)).perform(click())
            composeRule.waitForIdle()
        }else{
            val finalDateDesc = dayBuilder
                .append(",")
                .append(" ")
                .append(formattedDate).toString()

            onView(withContentDescription(finalDateDesc)).perform(click())
            composeRule.waitForIdle()
        }

        onView(withText("OK")).perform(click())
        composeRule.waitForIdle()

        val displayedDate = buildAnnotatedString {
            append(context.getString(R.string.session_from))
            append(" ")
            append(testDay.toString())
        }.toString()

        val displayedSet = "Set 1"

        val displayedVolume = buildAnnotatedString {
            append(context.getString(R.string.volume))
            append(" : ")
            append(440.0.toString())
            append(" ")
            append(context.getString(R.string.kg))
        }.toString()

        composeRule.onNodeWithText(displayedDate).assertIsDisplayed()
        composeRule.onNodeWithText(displayedSet).assertIsDisplayed()
        composeRule.onNodeWithText(displayedVolume).assertIsDisplayed()
    }
}