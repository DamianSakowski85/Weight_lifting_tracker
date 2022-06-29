package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.InvalidPlanException
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdatePlanUseCaseTest {
    private lateinit var updatePlanUseCase: UpdatePlanUseCase
    private lateinit var fakePlanRepository: FakePlanRepository
    private val planToInsert = Plan(
        1,
        "name",
        "desc",
        1.toLong()
    )


    @Before
    fun setup() = runBlocking {
        fakePlanRepository = FakePlanRepository()
        updatePlanUseCase = UpdatePlanUseCase(fakePlanRepository)

        fakePlanRepository.insertPlan(planToInsert)
    }

    @Test(expected = InvalidPlanException::class)
    fun `Update plan with blank name, exception test`() = runBlocking {
        val plan = fakePlanRepository.getPlanById(1)

        val planToUpdate = plan?.copy(
            planName = ""
        )

        if (planToUpdate != null) {
            updatePlanUseCase(planToUpdate)
        }
    }

    @Test
    fun `Update plan, is updated`() = runBlocking {
        val plan = fakePlanRepository.getPlanById(1)

        val planToUpdate = plan?.copy(
            planName = "updated name"
        )
        if (planToUpdate != null) {
            updatePlanUseCase(planToUpdate)
        }

        assertEquals("updated name",fakePlanRepository.getPlanById(1)?.planName)
    }
}