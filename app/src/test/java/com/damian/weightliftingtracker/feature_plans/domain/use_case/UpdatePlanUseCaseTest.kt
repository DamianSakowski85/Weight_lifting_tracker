package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.InvalidPlanException
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdatePlanUseCaseTest {
    private lateinit var updatePlanUseCase: UpdatePlanUseCase
    private lateinit var planRepository: PlanRepository
    private val planToInsert = Plan(
        1,
        "name",
        "desc",
        1.toLong()
    )


    @Before
    fun setup() = runBlocking {
        planRepository = FakePlanRepository()
        updatePlanUseCase = UpdatePlanUseCase(planRepository)

        planRepository.insertPlan(planToInsert)
    }

    @Test(expected = InvalidPlanException::class)
    fun `Update plan with blank name, exception test`() = runBlocking {
        val plan = planRepository.getPlanById(1)

        val planToUpdate = plan?.copy(
            planName = ""
        )

        if (planToUpdate != null) {
            updatePlanUseCase(planToUpdate)
        }
    }

    @Test
    fun `Update plan, is updated`() = runBlocking {
        val plan = planRepository.getPlanById(1)

        val planToUpdate = plan?.copy(
            planName = "updated name"
        )
        if (planToUpdate != null) {
            updatePlanUseCase(planToUpdate)
        }

        assertEquals("updated name",planRepository.getPlanById(1)?.planName)
    }
}