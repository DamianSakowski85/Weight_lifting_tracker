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
import com.damian.weightliftingtracker.feature_exercise.presentation.add_edit_exercise.AddEditExerciseScreen
import com.damian.weightliftingtracker.feature_exercise.presentation.utli.ExerciseScreens
import com.google.accompanist.navigation.animation.composable

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addEditExercise(
    navController: NavController
) {
    composable(
        route = ExerciseScreens.AddEditExerciseScreen.route +
                "sessionId={sessionId}&?exerciseId={exerciseId}",
        arguments = mutableListOf(
            navArgument(name = "sessionId") {
                type = NavType.IntType
            },
            navArgument(name = "exerciseId") {
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
        AddEditExerciseScreen(
            closeScreen = { navController.popBackStack() },
            viewModel = hiltViewModel()
        )
    }
}