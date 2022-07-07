package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeletePlanUseCaseTest {

    private lateinit var deletePlanUseCase: DeletePlanUseCase
    private lateinit var planRepository: PlanRepository

    @Before
    fun setup() = runBlocking {
        planRepository = FakePlanRepository()
        deletePlanUseCase = DeletePlanUseCase(planRepository)

        val planToDelete = Plan(
            1,
            "name",
            "desc",
            1
        )
        planRepository.insertPlan(planToDelete)
    }

    @Test
    fun `Delete plan, is deleted`() = runBlocking {
        deletePlanUseCase(1)

        assertEquals(null,planRepository.getPlanById(1))
    }
}