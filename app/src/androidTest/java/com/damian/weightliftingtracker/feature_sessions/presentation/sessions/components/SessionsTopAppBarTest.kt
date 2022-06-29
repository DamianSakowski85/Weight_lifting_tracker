package com.damian.weightliftingtracker.feature_sessions.presentation.sessions.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionsTopAppBarTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val title: String = "Test plan"
    private var navBack: Boolean = false

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            WeightLiftingTrackerTheme {
                SessionsTopAppBar(
                    title = title,
                    onBackArrowClick = {
                        navBack = true
                    })
            }
        }
    }

    @Test
    fun titleVisibilityTest() {
        composeRule.onNodeWithText(title).assertIsDisplayed()
    }

    @Test
    fun navigationTest() {
        composeRule.onNodeWithContentDescription(context.getString(R.string.nav_back))
            .performClick()
        Assert.assertTrue(navBack)
    }
}