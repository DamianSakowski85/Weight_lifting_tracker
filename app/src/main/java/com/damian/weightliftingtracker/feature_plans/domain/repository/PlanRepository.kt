package com.damian.weightliftingtracker.feature_plans.domain.repository

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    suspend fun getPlanById(id: Int): Plan?

    fun getPlans(): Flow<List<Plan>>

    fun getPreferences(): Flow<PlanSortPreferences>

    suspend fun updatePref(planSortPreferences: PlanSortPreferences)

    suspend fun insertPlan(plan: Plan)

    suspend fun updatePlan(plan: Plan)

    suspend fun deletePlanById(planId: Int)
}