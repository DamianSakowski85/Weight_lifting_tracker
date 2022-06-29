package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeletePlanUseCaseTest {

    private lateinit var deletePlanUseCase: DeletePlanUseCase
    private lateinit var fakePlanRepository: FakePlanRepository

    @Before
    fun setup() = runBlocking {
        fakePlanRepository = FakePlanRepository()
        deletePlanUseCase = DeletePlanUseCase(fakePlanRepository)

        val planToDelete = Plan(
            1,
            "name",
            "desc",
            1
        )
        fakePlanRepository.insertPlan(planToDelete)
    }

    @Test
    fun `Delete plan, is deleted`() = runBlocking {
        deletePlanUseCase(1)

        assertEquals(null,fakePlanRepository.getPlanById(1))
    }
}