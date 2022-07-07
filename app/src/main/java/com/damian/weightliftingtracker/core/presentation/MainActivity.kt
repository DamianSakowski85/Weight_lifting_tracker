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
import com.damian.weightliftingtracker.core.presentation.nav_graph_builder_components.*
import com.damian.weightliftingtracker.feature_plans.presentation.utli.PlanScreens
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






