package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.InvalidPlanException
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddPlanUseCaseTest {

    private lateinit var addPlansUseCase: AddPlanUseCase
    private lateinit var fakePlanRepository: FakePlanRepository

    @Before
    fun setup() {
        fakePlanRepository = FakePlanRepository()
        addPlansUseCase = AddPlanUseCase(fakePlanRepository)
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

        assertEquals(planToInsert,fakePlanRepository.getPlanById(1))
    }
}