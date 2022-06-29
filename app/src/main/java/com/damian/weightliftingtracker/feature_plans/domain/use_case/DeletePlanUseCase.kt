package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository

class DeletePlanUseCase constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: Int) {
        planRepository.deletePlanById(planId)
    }
}