package com.damian.weightliftingtracker.feature_plans.data.repository

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanDao
import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.data.data_source.PreferencesManager
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow

class PlanRepositoryImpl constructor(
    private val planDao: PlanDao,
    private val preferencesManager: PreferencesManager
) : PlanRepository {
    override suspend fun getPlanById(id: Int): Plan? = planDao.getPlanById(id)

    override fun getPlans(): Flow<List<Plan>> = planDao.getPlans()

    override fun getPreferences(): Flow<PlanSortPreferences> = preferencesManager.planOrderPref

    override suspend fun updatePref(planSortPreferences: PlanSortPreferences) =
        preferencesManager.updatePref(planSortPreferences)

    override suspend fun insertPlan(plan: Plan) = planDao.insert(plan)

    override suspend fun updatePlan(plan: Plan) = planDao.update(plan)

    override suspend fun deletePlanById(planId: Int) = planDao.deleteById(planId)
}