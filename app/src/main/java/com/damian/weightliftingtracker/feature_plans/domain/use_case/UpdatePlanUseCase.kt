package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.domain.model.InvalidPlanException
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository

class UpdatePlanUseCase constructor(
    private val planRepository: PlanRepository
) {
    @Throws(InvalidPlanException::class)
    suspend operator fun invoke(plan: Plan) {
        if (plan.planName.isBlank()) {
            throw InvalidPlanException("The name of the plan can't be empty.")
        }
        planRepository.updatePlan(plan)
    }
}