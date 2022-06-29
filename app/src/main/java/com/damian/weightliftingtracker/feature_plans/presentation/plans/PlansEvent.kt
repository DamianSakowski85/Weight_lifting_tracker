package com.damian.weightliftingtracker.feature_plans.presentation.plans

import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder

sealed class PlansEvent {
    object OnToggleOrderSection : PlansEvent()
    data class OnOrderChange(val planOrder: PlanOrder) : PlansEvent()

    data class OnPlanMenuClick(val selectedPlan: Plan) : PlansEvent()

    object OnAddPlan : PlansEvent()
    object OnEditPlan : PlansEvent()
    object OnDelete : PlansEvent()

    object OnConfirmDeletion : PlansEvent()
    object OnDismissDialog : PlansEvent()

    data class OnPlanClick(val planId: Int, val planName: String) : PlansEvent()
}
