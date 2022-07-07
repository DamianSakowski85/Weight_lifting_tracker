package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateSortOrderInPrefUseCaseTest {

    private lateinit var planRepository: PlanRepository
    private lateinit var updateSortOrderInPrefUseCase: UpdateSortOrderInPrefUseCase

    @Before
    fun setup() = runBlocking {
        planRepository = FakePlanRepository()
        updateSortOrderInPrefUseCase = UpdateSortOrderInPrefUseCase(planRepository)
    }

    @Test
    fun `Update sortOrderPref, is updated`() = runBlocking {
        updateSortOrderInPrefUseCase(PlanOrder.Name(OrderType.Ascending))

        assertEquals(true, planRepository.getPreferences().first().nameSelected)
        assertEquals(false, planRepository.getPreferences().first().dateSelected)
        assertEquals(true, planRepository.getPreferences().first().ascSelected)
        assertEquals(false, planRepository.getPreferences().first().descSelected)
    }
}