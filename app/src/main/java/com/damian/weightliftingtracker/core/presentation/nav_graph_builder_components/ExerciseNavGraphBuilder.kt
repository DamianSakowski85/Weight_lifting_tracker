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
import com.damian.weightliftingtracker.feature_exercise.presentation.exercises.ExerciseScreen
import com.damian.weightliftingtracker.feature_exercise.presentation.utli.ExerciseScreens
import com.damian.weightliftingtracker.feature_exercise_history.presentation.utli.DatePickerScreens
import com.damian.weightliftingtracker.feature_exercise_log.presentation.utli.ExerciseLogScreens
import com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.utli.VolumeScreens
import com.google.accompanist.navigation.animation.composable

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.exercises(
    navController: NavController
) {
    composable(
        route = ExerciseScreens.ExerciseScreen.route +
                "sessionId={sessionId}&sessionName={sessionName}",
        arguments = mutableListOf(
            navArgument(name = "sessionId") {
                type = NavType.IntType
            },
            navArgument(name = "sessionName") {
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
        ExerciseScreen(
            closeScreen = { navController.popBackStack() },
            navToAddExerciseScreen = { sessionId ->
                navController.navigate(
                    route = ExerciseScreens.AddEditExerciseScreen.route +
                            "sessionId=${sessionId}&?exerciseId"
                )
            },
            navToEditExerciseScreen = { sessionId, exerciseId ->
                navController.navigate(
                    ExerciseScreens.AddEditExerciseScreen.route +
                            "sessionId=${sessionId}&?exerciseId=${exerciseId}"
                )
            },
            navToExerciseLogScreen = { exerciseId, exerciseName ->
                navController.navigate(
                    ExerciseLogScreens.ExerciseLogScreen.route +
                            "exerciseId=${exerciseId}&exerciseName=${exerciseName}"
                )
            },
            navToChartsScreen = { exerciseId, exerciseName ->
                navController.navigate(
                    VolumeScreens.VolumeScreen.route +
                            "exerciseId=${exerciseId}&exerciseName=${exerciseName}"
                )
            },
            navToDatePickerScreen = { exerciseId, exerciseName ->
                navController.navigate(
                    DatePickerScreens.DatePickerScreen.route +
                            "exerciseId=${exerciseId}&exerciseName=${exerciseName}"
                )
            },
            viewModel = hiltViewModel()
        )
    }
}