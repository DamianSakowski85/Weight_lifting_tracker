package com.damian.weightliftingtracker.core.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.core.presentation.nav_graph_builder_components.*
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.use_case.*
import com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan.AddEditPlanScreen
import com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan.AddEditPlanViewModel
import com.damian.weightliftingtracker.feature_plans.presentation.plans.PlansScreen
import com.damian.weightliftingtracker.feature_plans.presentation.plans.PlansViewModel
import com.damian.weightliftingtracker.feature_plans.presentation.utli.PlanScreens
import com.damian.weightliftingtracker.feature_sessions.data.repository.FakeSessionRepository
import com.damian.weightliftingtracker.feature_sessions.domain.use_case.*
import com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session.AddEditSessionScreen
import com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session.AddEditSessionViewModel
import com.damian.weightliftingtracker.feature_sessions.presentation.sessions.SessionScreen
import com.damian.weightliftingtracker.feature_sessions.presentation.sessions.SessionsViewModel
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = PlanScreens.PlansScreen.route
        setContent {
            WeightLiftingTrackerTheme {
                MyApp(startDestination)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MyApp(launchDestination: String) {
    val navController = rememberAnimatedNavController()
    //val activity = LocalContext.current as AppCompatActivity
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        AnimatedNavHost(
            navController = navController,
            startDestination = launchDestination,
            builder = {
                plans(navController = navController)
                addEditPlan(navController = navController)
                sessions(navController = navController)
                addEditSession(navController = navController)
                exercises(navController = navController)
                addEditExercise(navController = navController)
                exerciseLog(navController = navController)
                lineChart(navController = navController)
                datePicker(navController = navController)
            }
        )
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

@Preview("Add Edit Plan Screen Preview")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun AddEditPlanScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
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


@Preview("Session Screen Preview")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun SessionScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            SessionScreen(
                closeScreen = { },
                navToAddSessionScreen = { },
                navToEditSessionScreen = { _, _ -> },
                navToDetails = { _, _ -> },
                viewModel = SessionsViewModel(
                    SessionUseCases(
                        getSessionsUseCase = GetSessionsUseCase(FakeSessionRepository()),
                        getSessionByIdUseCase = GetSessionByIdUseCase(FakeSessionRepository()),
                        addSessionUseCase = AddSessionUseCase(FakeSessionRepository()),
                        updateSessionUseCase = UpdateSessionUseCase(FakeSessionRepository()),
                        deleteSessionUseCase = DeleteSessionUseCase(FakeSessionRepository())
                    ),
                    SavedStateHandle(),
                    StringProvider(context)
                )
            )
        }
    }
}

@Preview("Add Edit Session Screen Preview")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun AddEditSessionScreenPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            val context = LocalContext.current
            AddEditSessionScreen(
                closeScreen = {},
                viewModelImpl = AddEditSessionViewModel(
                    SessionUseCases(
                        getSessionsUseCase = GetSessionsUseCase(FakeSessionRepository()),
                        getSessionByIdUseCase = GetSessionByIdUseCase(FakeSessionRepository()),
                        addSessionUseCase = AddSessionUseCase(FakeSessionRepository()),
                        updateSessionUseCase = UpdateSessionUseCase(FakeSessionRepository()),
                        deleteSessionUseCase = DeleteSessionUseCase(FakeSessionRepository())
                    ),
                    SavedStateHandle(),
                    StringProvider(context)
                )
            )
        }
    }
}






