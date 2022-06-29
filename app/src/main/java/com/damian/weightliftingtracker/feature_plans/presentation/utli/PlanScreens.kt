package com.damian.weightliftingtracker.feature_plans.presentation.utli


sealed class PlanScreens(val route: String) {
    object PlansScreen : PlanScreens("plans")
    object AddEditPlanPlanScreen : PlanScreens("add_edit_plan_screen")
}
