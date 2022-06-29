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
import com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker.ExerciseDatePickerScreen
import com.damian.weightliftingtracker.feature_exercise_history.presentation.utli.DatePickerScreens
import com.google.accompanist.navigation.animation.composable

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.datePicker(
    navController: NavController
) {
    composable(
        route = DatePickerScreens.DatePickerScreen.route +
                "exerciseId={exerciseId}&exerciseName={exerciseName}",
        arguments = mutableListOf(
            navArgument(name = "exerciseId") {
                type = NavType.IntType
            },
            navArgument(name = "exerciseName") {
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
        ExerciseDatePickerScreen(
            closeScreen = {
                navController.popBackStack()
            },
            viewModel = hiltViewModel()
        )
        //BarChartsVolumeScreen(lines = listOf(dataPoints1, dataPoints2))
    }
}