package com.damian.weightliftingtracker.feature_plans.presentation.plans

import androidx.compose.foundation.clickable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.presentation.plans.components.PlanItem
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlanItemTest {

    private val _state = mutableStateOf(PlansTestState())
    private val state: State<PlansTestState> = _state

    private val planToAdd = Plan(
        0,
        "Fbw",
        planDescription = "Full Body Workout",
        timestamp = 0
    )
    private var isMenuVisible : Boolean = false

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            WeightLiftingTrackerTheme {
                PlanItem(
                    plan = planToAdd,
                    modifier = Modifier.clickable {
                        _state.value = state.value.copy(
                            selectedPlan = planToAdd
                        )
                    },
                    onMenuClick = { isMenuVisible = true },
                )
            }
        }
    }

    @Test
    fun itemClickTest(){
        composeRule.onNodeWithContentDescription(planToAdd.planName).assertHasClickAction()
        composeRule.onNodeWithContentDescription(planToAdd.planName).performClick()
        Assert.assertEquals(state.value.selectedPlan,planToAdd)
    }

    @Test
    fun menuClickTest(){
        composeRule.onNodeWithTag(planToAdd.planName).assertHasClickAction()
        composeRule.onNodeWithTag(planToAdd.planName).performClick()
        Assert.assertTrue(isMenuVisible)
    }
}