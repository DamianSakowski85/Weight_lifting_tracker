package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPlanByIdUseCaseTest {
    private lateinit var getPlanByIdUseCase: GetPlanByIdUseCase
    private lateinit var planRepository: PlanRepository

    private val planToInsert = Plan(
        1,
        "name",
        "desc",
        1
    )

    @Before
    fun setup() = runBlocking {
        planRepository = FakePlanRepository()
        getPlanByIdUseCase = GetPlanByIdUseCase(planRepository)


        planRepository.insertPlan(planToInsert)
    }

    @Test
    fun `Get plan by id`() = runBlocking {
       val insertedPlan = getPlanByIdUseCase(1)

        assertEquals(insertedPlan,planToInsert)
    }
}