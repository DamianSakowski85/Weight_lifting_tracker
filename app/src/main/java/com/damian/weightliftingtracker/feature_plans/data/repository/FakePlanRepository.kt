package com.damian.weightliftingtracker.feature_plans.data.repository

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePlanRepository : PlanRepository {

    private val plans = mutableListOf<Plan>()
    private var _planSortPreferences: PlanSortPreferences = PlanSortPreferences(
        nameSelected = true,
        ascSelected = true,
        dateSelected = false,
        descSelected = false
    )

    override suspend fun getPlanById(id: Int): Plan? {
        return plans.find { it.id == id }
    }

    override fun getPlans(): Flow<List<Plan>> {
        return flow { emit(plans) }
    }

    override fun getPreferences(): Flow<PlanSortPreferences> {
        return flow { emit(_planSortPreferences) }
    }

    override suspend fun updatePref(planSortPreferences: PlanSortPreferences) {
        this._planSortPreferences = planSortPreferences
    }

    override suspend fun insertPlan(plan: Plan) {
        plans.add(plan)
    }

    override suspend fun updatePlan(plan: Plan) {
        val planToRemove = plans.find { it.id == plan.id }
        plans.remove(planToRemove)

        plans.add(plan)
    }

    override suspend fun deletePlanById(planId: Int) {
        val plan = plans.find { it.id == planId }
        plans.remove(plan)
    }
}