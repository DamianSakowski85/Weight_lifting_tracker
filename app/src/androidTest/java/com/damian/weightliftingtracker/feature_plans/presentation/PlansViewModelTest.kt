package com.damian.weightliftingtracker.feature_plans.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.use_case.*
import com.damian.weightliftingtracker.feature_plans.presentation.plans.PlansEvent
import com.damian.weightliftingtracker.feature_plans.presentation.plans.PlansViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlansViewModelTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var viewModel: PlansViewModel
    private val testPlan = Plan(1, "a", "b", 1)

    private lateinit var fakeRepository: FakePlanRepository

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
       // preferencesManagerTest = PreferencesManager(context)
        fakeRepository = FakePlanRepository()

        viewModel = PlansViewModel(
            PlanUseCases(
                getPlanUseCases = GetPlansUseCase(fakeRepository),
                deletePlanUseCase = DeletePlanUseCase(fakeRepository),
                addPlanUseCase = AddPlanUseCase(fakeRepository),
                updatePlanUseCase = UpdatePlanUseCase(fakeRepository),
                getPlanByIdUseCase = GetPlanByIdUseCase(fakeRepository),
                getSortOrderFromPrefUseCase = GetSortOrderFromPrefUseCase(fakeRepository),
                updateSortOrderInPrefUseCase = UpdateSortOrderInPrefUseCase(fakeRepository)
            ),
            StringProvider(context)
        )
    }

    @Test
    fun onAddPlanEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(PlansEvent.OnAddPlan)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(PlansViewModel.UiEvent.NavToCreatePlan, event)
        }
    }

    @Test
    fun onPlanMenuClickEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(PlansEvent.OnPlanMenuClick(testPlan))

            Assert.assertEquals(testPlan, viewModel.state.value.selectedPlan)

            val event = viewModel.eventFlow.first()
            Assert.assertEquals(PlansViewModel.UiEvent.OpenContextMenu(testPlan.planName), event)
        }
    }

    @Test
    fun onEditPlanEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(PlansEvent.OnPlanMenuClick(testPlan))
            viewModel.onEvent(PlansEvent.OnEditPlan)

            val event = viewModel.eventFlow.take(3).toList()

            Assert.assertEquals(PlansViewModel.UiEvent.OpenContextMenu(testPlan.planName), event[0])
            Assert.assertEquals(PlansViewModel.UiEvent.CloseContextMenu, event[1])
            Assert.assertEquals(PlansViewModel.UiEvent.NavToUpdateScreen(testPlan.id), event[2])
        }
    }

    @Test
    fun onDeleteEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(PlansEvent.OnDelete)
            val event = viewModel.eventFlow.first()

            Assert.assertEquals(PlansViewModel.UiEvent.CloseContextMenu, event)
            Assert.assertTrue(viewModel.state.value.isDeleteDialogVisible)
        }
    }

    @Test
    fun onConfirmDeletionTest() {
        testCoroutineRule.runBlockingTest {
            launch {
                viewModel.onEvent(PlansEvent.OnPlanMenuClick(testPlan))
                viewModel.onEvent(PlansEvent.OnDelete)
                viewModel.onEvent(PlansEvent.OnConfirmDeletion)

                viewModel.eventFlow.collect {
                    if (it == PlansViewModel.UiEvent.ShowMessage(
                            testPlan.planName + " " + context.getString(
                                R.string.deleted
                            )
                        )
                    ) {
                        this.cancel()
                    }
                }
            }

            Assert.assertFalse(viewModel.state.value.isDeleteDialogVisible)
            Assert.assertNull(viewModel.state.value.selectedPlan)
        }
    }

    @Test
    fun onDismissDialogTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(PlansEvent.OnPlanMenuClick(testPlan))
            viewModel.onEvent(PlansEvent.OnDismissDialog)

            Assert.assertFalse(viewModel.state.value.isDeleteDialogVisible)
            Assert.assertNull(viewModel.state.value.selectedPlan)
        }
    }

    @Test
    fun onPlanClickTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(PlansEvent.OnPlanClick(testPlan.id, testPlan.planName))
            val event = viewModel.eventFlow.first()

            Assert.assertEquals(
                event,
                PlansViewModel.UiEvent.NavToItemDetails(testPlan.id, testPlan.planName)
            )
        }
    }

    @Test
    fun onToggleOrderSectionTest() {
        viewModel.onEvent(PlansEvent.OnToggleOrderSection)
        Assert.assertTrue(viewModel.state.value.isOrderSectionVisible)
    }
}