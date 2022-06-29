package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPlansUseCase(
    private val planRepository: PlanRepository
) {
    private fun convertPreferences(preferences: PlanSortPreferences): PlanOrder {
        return if (preferences.nameSelected && preferences.ascSelected) {
            PlanOrder.Name(OrderType.Ascending)
        } else if (preferences.nameSelected && preferences.descSelected) {
            PlanOrder.Name(OrderType.Descending)
        } else if (preferences.dateSelected && preferences.ascSelected) {
            PlanOrder.Date(OrderType.Ascending)
        } else {
            PlanOrder.Date(OrderType.Descending)
        }
    }

    operator fun invoke(): Flow<List<Plan>> {
        return combine(
            planRepository.getPlans(),
            planRepository.getPreferences()
        ) { plans, pref ->

            val sortOrder = convertPreferences(pref)

            when (sortOrder.orderType) {
                is OrderType.Ascending -> {
                    when (sortOrder) {
                        is PlanOrder.Name -> plans.sortedBy { it.planName.lowercase() }
                        is PlanOrder.Date -> plans.sortedBy { it.timestamp }
                    }
                }
                is OrderType.Descending -> {
                    when (sortOrder) {
                        is PlanOrder.Name -> plans.sortedByDescending { it.planName.lowercase() }
                        is PlanOrder.Date -> plans.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }
}