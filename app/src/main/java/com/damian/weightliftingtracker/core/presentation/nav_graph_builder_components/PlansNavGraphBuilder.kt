package com.damian.weightliftingtracker.core.presentation.nav_graph_builder_components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.damian.weightliftingtracker.feature_plans.presentation.plans.PlansScreen
import com.damian.weightliftingtracker.feature_plans.presentation.utli.PlanScreens
import com.damian.weightliftingtracker.feature_sessions.presentation.utli.SessionScreens
import com.google.accompanist.navigation.animation.composable

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.plans(
    navController: NavController
) {
    composable(
        route = PlanScreens.PlansScreen.route,
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
    ) {
        PlansScreen(
            navToCreatePlanScreen = {
                navController.navigate(route = PlanScreens.AddEditPlanPlanScreen.route)
            },
            navToSessionsScreen = { planId, planName ->
                navController.navigate(
                    SessionScreens.SessionScreen.route +
                            "planId=${planId}&planName=${planName}"
                )
            },
            navToUpdateScreen = { planId ->
                navController.navigate(
                    PlanScreens.AddEditPlanPlanScreen.route +
                            "?planId=${planId}"
                )
            },
            viewModel = hiltViewModel()
        )
    }
}