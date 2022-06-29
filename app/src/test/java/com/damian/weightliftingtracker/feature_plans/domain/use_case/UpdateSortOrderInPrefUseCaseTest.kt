package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateSortOrderInPrefUseCaseTest {

    private lateinit var fakePlanRepository: FakePlanRepository
    private lateinit var updateSortOrderInPrefUseCase: UpdateSortOrderInPrefUseCase

    @Before
    fun setup() = runBlocking {
        fakePlanRepository = FakePlanRepository()
        updateSortOrderInPrefUseCase = UpdateSortOrderInPrefUseCase(fakePlanRepository)
    }

    @Test
    fun `Update sortOrderPref, is updated`() = runBlocking {
        updateSortOrderInPrefUseCase(PlanOrder.Name(OrderType.Ascending))

        assertEquals(true, fakePlanRepository.getPreferences().first().nameSelected)
        assertEquals(false, fakePlanRepository.getPreferences().first().dateSelected)
        assertEquals(true, fakePlanRepository.getPreferences().first().ascSelected)
        assertEquals(false, fakePlanRepository.getPreferences().first().descSelected)
    }
}