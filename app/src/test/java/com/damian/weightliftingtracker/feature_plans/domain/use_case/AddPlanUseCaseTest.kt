package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.InvalidPlanException
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddPlanUseCaseTest {

    private lateinit var addPlansUseCase: AddPlanUseCase
    private lateinit var planRepository: PlanRepository

    @Before
    fun setup() {
        planRepository = FakePlanRepository()
        addPlansUseCase = AddPlanUseCase(planRepository)
    }

    @Test(expected = InvalidPlanException::class)
    fun `Insert plan with blank name, exception test`() = runBlocking {
        val planToInsert = Plan(
            1,
            "",
            "",
            1.toLong()
        )
        addPlansUseCase(planToInsert)
    }

    @Test
    fun `Insert plan, is inserted`() = runBlocking {
        val planToInsert = Plan(
            1,
            "name",
            "desc",
            1.toLong()
        )
        addPlansUseCase(planToInsert)

        assertEquals(planToInsert,planRepository.getPlanById(1))
    }
}