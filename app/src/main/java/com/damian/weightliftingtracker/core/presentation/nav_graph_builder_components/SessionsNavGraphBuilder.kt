package com.damian.weightliftingtracker.core.presentation.nav_graph_builder_components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.damian.weightliftingtracker.feature_exercise.presentation.utli.ExerciseScreens
import com.damian.weightliftingtracker.feature_sessions.presentation.sessions.SessionScreen
import com.damian.weightliftingtracker.feature_sessions.presentation.utli.SessionScreens
import com.google.accompanist.navigation.animation.composable

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.sessions(
    navController: NavController
) {
    composable(
        route = SessionScreens.SessionScreen.route +
                "planId={planId}&planName={planName}",
        arguments = mutableListOf(
            navArgument(name = "planId") {
                type = NavType.IntType
            },

            navArgument(name = "planName") {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing,

                    )
            ) + fadeIn(animationSpec = tween(300))
        },

        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { 0 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },

        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
    ) {
        SessionScreen(
            closeScreen = { navController.popBackStack() },
            navToAddSessionScreen = { planId ->
                navController.navigate(
                    route = SessionScreens.AddEditSessionScreen.route +
                            "planId=${planId}&?sessionId"
                )
            },
            navToEditSessionScreen = { planId, sessionId ->
                navController.navigate(
                    SessionScreens.AddEditSessionScreen.route +
                            "planId=${planId}&?sessionId=${sessionId}"
                )
            },
            navToDetails = { sessionId, sessionName ->
                navController.navigate(
                    ExerciseScreens.ExerciseScreen.route +
                            "sessionId=${sessionId}&sessionName=${sessionName}"
                )
            },
            viewModel = hiltViewModel()
        )
    }
}