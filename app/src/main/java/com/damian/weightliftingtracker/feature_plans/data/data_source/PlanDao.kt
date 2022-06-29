package com.damian.weightliftingtracker.feature_plans.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {

    @Insert
    suspend fun insert(plan: Plan)

    @Update
    suspend fun update(plan: Plan)

    @Query("DELETE FROM _plan WHERE id=:planId")
    suspend fun deleteById(planId: Int)

    @Query("SELECT * FROM _plan WHERE id=:id")
    suspend fun getPlanById(id: Int): Plan?

    @Query("SELECT * FROM _plan")
    fun getPlans(): Flow<List<Plan>>
}