package com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.presentation.components.TextFieldsCard
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.use_case.*
import com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan.components.AddEditPlanTopAppBar
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AddEditPlanScreen(
    closeScreen: () -> Unit,
    viewModel: AddEditPlanViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditPlanViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(
                        context, event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is AddEditPlanViewModel.UiEvent.NavBack -> {
                    keyboardController?.hide()
                    closeScreen()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AddEditPlanTopAppBar(closeAction = { viewModel.onEvent(AddEditPlanEvent.OnClose) })
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(imageVector = Icons.Default.Save, stringResource(id = R.string.save_plan))
                },
                onClick = { viewModel.onEvent(AddEditPlanEvent.OnSavePlan) },
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        },
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier.padding(vertical = 8.dp)) {
            TextFieldsCard(
                viewModel.addEditPlanState.value.name,
                viewModel.addEditPlanState.value.description,
                onEnterName = { value ->
                    viewModel.onEvent(AddEditPlanEvent.OnEnterName(value))
                },
                onEnterDescription = { value ->
                    viewModel.onEvent(AddEditPlanEvent.OnEnterDescription(value))
                }
            )
        }
    }
}

@Preview("Add Edit Plan Screen Preview")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun AddEditPlanScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            val context = LocalContext.current
            AddEditPlanScreen(
                closeScreen = {},
                viewModel = AddEditPlanViewModel(
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
                    SavedStateHandle(),
                    StringProvider(context)
                )
            )
        }
    }
}

