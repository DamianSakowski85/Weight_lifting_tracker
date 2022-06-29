package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPlanByIdUseCaseTest {
    private lateinit var getPlanByIdUseCase: GetPlanByIdUseCase
    private lateinit var fakePlanRepository: FakePlanRepository

    private val planToInsert = Plan(
        1,
        "name",
        "desc",
        1
    )

    @Before
    fun setup() = runBlocking {
        fakePlanRepository = FakePlanRepository()
        getPlanByIdUseCase = GetPlanByIdUseCase(fakePlanRepository)


        fakePlanRepository.insertPlan(planToInsert)
    }

    @Test
    fun `Get plan by id`() = runBlocking {
       val insertedPlan = getPlanByIdUseCase(1)

        assertEquals(insertedPlan,planToInsert)
    }
}