package com.damian.weightliftingtracker.core.presentation.nav_graph_builder_components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session.AddEditSessionScreen
import com.damian.weightliftingtracker.feature_sessions.presentation.utli.SessionScreens
import com.google.accompanist.navigation.animation.composable

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addEditSession(
    navController: NavController
) {
    composable(
        route = SessionScreens.AddEditSessionScreen.route +
                "planId={planId}&?sessionId={sessionId}",
        arguments = mutableListOf(
            navArgument(name = "planId") {
                type = NavType.IntType
            },
            navArgument(name = "sessionId") {
                type = NavType.IntType
                defaultValue = -1
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
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
    ) {
        AddEditSessionScreen(
            closeScreen = { navController.popBackStack() },
            viewModelImpl = hiltViewModel()
        )
    }
}
