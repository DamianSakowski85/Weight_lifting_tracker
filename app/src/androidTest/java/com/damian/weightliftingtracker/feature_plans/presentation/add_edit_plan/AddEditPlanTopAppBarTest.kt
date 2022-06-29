package com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan.components.AddEditPlanTopAppBar
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddEditPlanTopAppBarTest {
    private var isCloseButtonClicked: Boolean = false
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            WeightLiftingTrackerTheme {
                AddEditPlanTopAppBar(closeAction = {
                    isCloseButtonClicked = true
                })
            }
        }
    }

    @Test
    fun toggleOrderSectionClickAndStateUpdateTest() {
        //check the initial state
        Assert.assertFalse(isCloseButtonClicked)
        //check if sort button has click action
        composeRule.onNodeWithContentDescription(context.getString(R.string.close))
            .assertHasClickAction()
        //click close button
        composeRule.onNodeWithContentDescription(context.getString(R.string.close))
            .performClick()
        //check if order section is now visible
        Assert.assertTrue(isCloseButtonClicked)
    }
}