package com.damian.weightliftingtracker.feature_plans.presentation.plans

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.feature_plans.presentation.plans.components.PlansTopAppBar
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlansTopAppBarTest {
    private val _state = mutableStateOf(PlansTestState())
    private val state: State<PlansTestState> = _state
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @get: Rule
    val composeRule = createComposeRule()


    @Before
    fun setUp() {
        composeRule.setContent {
            WeightLiftingTrackerTheme {
                PlansTopAppBar(onToggleOrderSection = {
                    if (state.value.isDeleteDialogVisible){
                        _state.value = state.value.copy(
                            isOrderSectionVisible = false
                        )
                    }
                    else{
                        _state.value = state.value.copy(
                            isOrderSectionVisible = true
                        )
                    }
                })
            }
        }
    }

    @Test
    fun toggleOrderSectionClickAnsStateUpdateTest() {
        //check if sort button has click action
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort))
            .assertHasClickAction()
        //check if order section is not visible
        assertFalse(state.value.isOrderSectionVisible)
        //click toggle section
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort))
            .performClick()
        //check if order section is now visible
        assertTrue(state.value.isOrderSectionVisible)
        //click toggle section again
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort))
            .performClick()
        //order section is now not visible
        assertTrue(state.value.isOrderSectionVisible)
    }
}