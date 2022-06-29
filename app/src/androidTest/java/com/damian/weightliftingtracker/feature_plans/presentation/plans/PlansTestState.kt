package com.damian.weightliftingtracker.feature_plans.presentation.plans

import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder

data class PlansTestState(
    val plans : List<Plan> = emptyList(),
    val selectedPlan : Plan? = null,
    val planOrder: PlanOrder = PlanOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isDeleteDialogVisible : Boolean = false,
    val isEmptyListLabelVisible : Boolean = false
)
