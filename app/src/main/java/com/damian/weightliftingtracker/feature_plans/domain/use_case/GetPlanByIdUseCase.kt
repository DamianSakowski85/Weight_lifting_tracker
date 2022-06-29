package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository

class GetPlanByIdUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(id: Int): Plan? {
        return planRepository.getPlanById(id)
    }
}