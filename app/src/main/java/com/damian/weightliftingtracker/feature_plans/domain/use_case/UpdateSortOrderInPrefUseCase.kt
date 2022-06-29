package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder

class UpdateSortOrderInPrefUseCase(private val planRepository: PlanRepository) {
    suspend operator fun invoke(planOrder: PlanOrder) {
        when (planOrder.orderType) {
            is OrderType.Ascending -> {
                when (planOrder) {
                    is PlanOrder.Name -> {
                        val planPreferences = PlanSortPreferences(
                            nameSelected = true,
                            dateSelected = false,
                            ascSelected = true,
                            descSelected = false
                        )
                        planRepository.updatePref(planPreferences)
                    }
                    is PlanOrder.Date -> {
                        val planPreferences = PlanSortPreferences(
                            nameSelected = false,
                            dateSelected = true,
                            ascSelected = true,
                            descSelected = false
                        )
                        planRepository.updatePref(planPreferences)
                    }
                }
            }
            is OrderType.Descending -> {
                when (planOrder) {
                    is PlanOrder.Name -> {
                        val planPreferences = PlanSortPreferences(
                            nameSelected = true,
                            dateSelected = false,
                            ascSelected = false,
                            descSelected = true
                        )
                        planRepository.updatePref(planPreferences)
                    }
                    is PlanOrder.Date -> {

                        val planPreferences = PlanSortPreferences(
                            nameSelected = false,
                            dateSelected = true,
                            ascSelected = false,
                            descSelected = true
                        )
                        planRepository.updatePref(planPreferences)
                    }
                }
            }
        }
    }
}