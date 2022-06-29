package com.damian.weightliftingtracker.feature_plans.presentation.plans

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import com.damian.weightliftingtracker.feature_plans.presentation.plans.components.OrderSection
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class OrderSectionTest {

    private val _state = mutableStateOf(PlansTestState())
    private val state: State<PlansTestState> = _state
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @get: Rule
    val composeRule = createComposeRule()


    @Before
    fun setUp() {
        composeRule.setContent {
            WeightLiftingTrackerTheme {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.ORDER_SECTION),
                    planOrder = state.value.planOrder,
                    onOrderChange = {
                        _state.value = state.value.copy(
                            planOrder = it
                        )
                    })
            }
        }
    }

    @Test
    fun sortByNameAscAndUpdateStateTest() {

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .performClick()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .performClick()

        //check if correct buttons are selected
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .assertIsSelected()

        //check if there is no more than two buttons selected at the same time
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .assertIsNotSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .assertIsNotSelected()

        //check if state is correctly updated
        assertEquals(PlanOrder.Name::class,_state.value.planOrder::class)
        assertEquals(OrderType.Ascending::class,_state.value.planOrder.orderType::class)
    }

    @Test
    fun sortByNameDescAndUpdateStateTest() {
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .performClick()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .performClick()

        //check if correct buttons are selected
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .assertIsSelected()

        //check if there is no more than two buttons selected at the same time
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .assertIsNotSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .assertIsNotSelected()

        //check if state is correctly updated
        assertEquals(PlanOrder.Name::class,_state.value.planOrder::class)
        assertEquals(OrderType.Descending::class,_state.value.planOrder.orderType::class)
    }

    @Test
    fun sortByDateAscAndStateUpdateTest() {
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .performClick()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .performClick()

        //check if correct buttons are selected
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .assertIsSelected()

        //check if there is no more than two buttons selected at the same time
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .assertIsNotSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .assertIsNotSelected()

        //check if state is correctly updated
        assertEquals(PlanOrder.Date::class,_state.value.planOrder::class)
        assertEquals(OrderType.Ascending::class,_state.value.planOrder.orderType::class)
    }

    @Test
    fun sortByDateDescAndStateUpdateTest() {
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .performClick()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .performClick()

        //check if correct buttons are selected
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_date))
            .assertIsSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.desc))
            .assertIsSelected()

        //check if there is no more than two buttons selected at the same time
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_by_name))
            .assertIsNotSelected()
        composeRule.onNodeWithContentDescription(context.getString(R.string.asc))
            .assertIsNotSelected()

        //check if state is correctly updated
        assertEquals(PlanOrder.Date::class,_state.value.planOrder::class)
        assertEquals(OrderType.Descending::class,_state.value.planOrder.orderType::class)
    }
}