package com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan

sealed class AddEditPlanEvent {
    data class OnEnterName(val value: String) : AddEditPlanEvent()
    data class OnEnterDescription(val value: String) : AddEditPlanEvent()
    object OnSavePlan : AddEditPlanEvent()
    object OnClose : AddEditPlanEvent()
}