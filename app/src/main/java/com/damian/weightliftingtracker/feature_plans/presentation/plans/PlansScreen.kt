package com.damian.weightliftingtracker.feature_plans.presentation.plans

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.presentation.components.DeleteDialog
import com.damian.weightliftingtracker.core.presentation.components.EditDeleteMenu
import com.damian.weightliftingtracker.core.presentation.components.EmptyListLabel
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.use_case.*
import com.damian.weightliftingtracker.feature_plans.presentation.plans.components.OrderSection
import com.damian.weightliftingtracker.feature_plans.presentation.plans.components.PlanItem
import com.damian.weightliftingtracker.feature_plans.presentation.plans.components.PlansTopAppBar
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun PlansScreen(
    navToCreatePlanScreen: () -> Unit,
    navToSessionsScreen: (planId: Int, planName: String) -> Unit,
    navToUpdateScreen: (planId: Int) -> Unit,
    viewModel: PlansViewModel
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val planState = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PlansViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(
                        context, event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is PlansViewModel.UiEvent.NavToCreatePlan -> {
                    navToCreatePlanScreen()
                }
                is PlansViewModel.UiEvent.NavToItemDetails -> {
                    navToSessionsScreen(event.planId, event.planName)
                }
                is PlansViewModel.UiEvent.NavToUpdateScreen -> {
                    navToUpdateScreen(event.planId)
                }
                is PlansViewModel.UiEvent.OpenContextMenu -> {
                    scope.launch {
                        modalBottomSheetState.show()
                    }
                }
                is PlansViewModel.UiEvent.CloseContextMenu -> {
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            }
        }
    }

    DeleteDialog(
        title = planState.selectedPlan?.planName ?: "",
        message = stringResource(id = R.string.delete_selected_plan),
        isVisible = planState.isDeleteDialogVisible,
        onConfirmClick = {
            viewModel.onEvent(PlansEvent.OnConfirmDeletion)
        },
        onDismissClick = {
            viewModel.onEvent(PlansEvent.OnDismissDialog)
        },
        onDismissRequest = {
            viewModel.onEvent(PlansEvent.OnDismissDialog)
        }
    )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            EditDeleteMenu(
                title = viewModel.state.value.selectedPlan?.planName,
                onUpdateClick = {
                    viewModel.onEvent(PlansEvent.OnEditPlan)
                },
                onDeleteClick = {
                    viewModel.onEvent(PlansEvent.OnDelete)
                },
                modifier = Modifier.testTag(TestTags.EDIT_DELETE_MENU_SECTION)
            )
        },
        modifier = Modifier.testTag(TestTags.PLANS_MODAL_BOTTOM_SHEET_LAYOUT)
    ) {
        Scaffold(
            //
            topBar = {
                PlansTopAppBar(
                    onToggleOrderSection = {
                        viewModel.onEvent(PlansEvent.OnToggleOrderSection)
                    })
            },
            floatingActionButton = {
                FloatingActionButton(
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.create_plan)
                        )
                    },
                    onClick = {
                        viewModel.onEvent(PlansEvent.OnAddPlan)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
            },
            //scaffoldState = scaffoldState
        ) {
            Column {

                AnimatedVisibility(
                    visible = planState.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(TestTags.ORDER_SECTION),
                        planOrder = planState.planOrder,
                        onOrderChange = { orderType ->
                            viewModel.onEvent(PlansEvent.OnOrderChange(orderType))
                        }
                    )
                }

                EmptyListLabel(
                    isListEmpty = viewModel.state.value.isEmptyListLabelVisible,
                    stringResource(id = R.string.you_have_no_plans),
                    animationDuration = 0,
                    modifier = Modifier.fillMaxSize()
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(planState.plans) { plan ->
                        PlanItem(
                            plan = plan,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(PlansEvent.OnPlanClick(plan.id, plan.planName))
                            },
                            onMenuClick = {
                                viewModel.onEvent(PlansEvent.OnPlanMenuClick(plan))
                            },
                        )
                        Divider(startIndent = 0.dp)
                    }
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Preview("Plan Screen Preview")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun PlanScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            PlansScreen(
                navToCreatePlanScreen = {},
                navToSessionsScreen = { _, _ -> },
                navToUpdateScreen = { },
                viewModel = PlansViewModel(
                    PlanUseCases(
                        getPlanUseCases = GetPlansUseCase(FakePlanRepository()),
                        deletePlanUseCase = DeletePlanUseCase(FakePlanRepository()),
                        addPlanUseCase = AddPlanUseCase(FakePlanRepository()),
                        updatePlanUseCase = UpdatePlanUseCase(FakePlanRepository()),
                        getPlanByIdUseCase = GetPlanByIdUseCase(FakePlanRepository()),
                        getSortOrderFromPrefUseCase = GetSortOrderFromPrefUseCase(FakePlanRepository()),
                        updateSortOrderInPrefUseCase = UpdateSortOrderInPrefUseCase(
                            FakePlanRepository()
                        )
                    ),
                    StringProvider(context)
                )
            )
        }
    }
}
