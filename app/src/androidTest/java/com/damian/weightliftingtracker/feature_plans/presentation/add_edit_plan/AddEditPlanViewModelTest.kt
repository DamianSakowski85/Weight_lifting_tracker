package com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.use_case.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddEditPlanViewModelTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var viewModel : AddEditPlanViewModel
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setup(){
        viewModel = AddEditPlanViewModel(
            PlanUseCases(
                getPlanUseCases =  GetPlansUseCase(FakePlanRepository()),
                deletePlanUseCase =  DeletePlanUseCase(FakePlanRepository()),
                addPlanUseCase = AddPlanUseCase(FakePlanRepository()),
                updatePlanUseCase = UpdatePlanUseCase(FakePlanRepository()),
                getPlanByIdUseCase = GetPlanByIdUseCase(FakePlanRepository()),
                getSortOrderFromPrefUseCase = GetSortOrderFromPrefUseCase(FakePlanRepository()),
                updateSortOrderInPrefUseCase = UpdateSortOrderInPrefUseCase(
                    FakePlanRepository()
                )
            ),
            SavedStateHandle(),
            StringProvider(context)
        )
    }

    @Test
    fun onEnterNameEventTest(){
        viewModel.onEvent(AddEditPlanEvent.OnEnterName("test name"))
        Assert.assertEquals("test name", viewModel.addEditPlanState.value.name)
    }

    @Test
    fun onEnterDescriptionEventTest(){
        viewModel.onEvent(AddEditPlanEvent.OnEnterDescription("test description"))
        Assert.assertEquals("test description", viewModel.addEditPlanState.value.description)
    }

    @Test
    fun onSavePlanEventTest() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(AddEditPlanEvent.OnEnterName("test name"))
            viewModel.onEvent(AddEditPlanEvent.OnEnterDescription("test description"))
            viewModel.onEvent(AddEditPlanEvent.OnSavePlan)

            val events = viewModel.eventFlow.take(2).toList()

            Assert.assertEquals(
                AddEditPlanViewModel.UiEvent.ShowMessage(context.getString(R.string.plan_added)),
                events[0]
            )

            Assert.assertEquals(
                AddEditPlanViewModel.UiEvent.NavBack,
                events[1]
            )
        }
    }

    @Test
    fun onCloseEvent() {
        testCoroutineRule.runBlockingTest {
            viewModel.onEvent(AddEditPlanEvent.OnClose)

            val event = viewModel.eventFlow.first()

            Assert.assertEquals(
                AddEditPlanViewModel.UiEvent.NavBack,
                event
            )
        }
    }
}