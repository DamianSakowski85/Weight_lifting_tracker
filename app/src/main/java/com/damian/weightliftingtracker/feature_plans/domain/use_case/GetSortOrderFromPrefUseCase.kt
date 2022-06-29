package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSortOrderFromPrefUseCase(private val planRepository: PlanRepository) {
    operator fun invoke(): Flow<PlanOrder> {
        return planRepository.getPreferences().map { preferences ->
            if (preferences.nameSelected && preferences.ascSelected) {
                return@map PlanOrder.Name(OrderType.Ascending)
            } else if (preferences.nameSelected && preferences.descSelected) {
                return@map PlanOrder.Name(OrderType.Descending)
            } else if (preferences.dateSelected && preferences.ascSelected) {
                return@map PlanOrder.Date(OrderType.Ascending)
            } else {
                return@map PlanOrder.Date(OrderType.Descending)
            }
        }
    }
}