package com.damian.weightliftingtracker.feature_plans.domain.use_case

data class PlanUseCases(
    val getPlanUseCases: GetPlansUseCase,
    val getPlanByIdUseCase: GetPlanByIdUseCase,
    val deletePlanUseCase: DeletePlanUseCase,
    val addPlanUseCase: AddPlanUseCase,
    val updatePlanUseCase: UpdatePlanUseCase,
    val getSortOrderFromPrefUseCase: GetSortOrderFromPrefUseCase,
    val updateSortOrderInPrefUseCase: UpdateSortOrderInPrefUseCase
)