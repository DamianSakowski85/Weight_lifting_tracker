package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.utli.OrderType
import com.damian.weightliftingtracker.feature_plans.domain.utli.PlanOrder
import org.junit.Assert.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSortOrderPrefUseCaseTest {
    private lateinit var planRepository: PlanRepository
    private lateinit var getSortOrderFromPrefUseCase: GetSortOrderFromPrefUseCase

    @Before
    fun setup() = runBlocking {
        planRepository = FakePlanRepository()
        getSortOrderFromPrefUseCase = GetSortOrderFromPrefUseCase(planRepository)
    }

    @Test
    fun `Get Name Asc, correct order`() = runBlocking {
        val planSortPreferences = PlanSortPreferences(
            nameSelected = true,
            dateSelected = false,
            ascSelected = true,
            descSelected = false
        )
        planRepository.updatePref(planSortPreferences)

        val pref = getSortOrderFromPrefUseCase().first()

        assertEquals(PlanOrder.Name::class,pref::class)
        assertEquals(OrderType.Ascending::class,pref.orderType::class)
    }

    @Test
    fun `Get Name Desc, correct order`() = runBlocking {
        val planSortPreferences = PlanSortPreferences(
            nameSelected = true,
            dateSelected = false,
            ascSelected = false,
            descSelected = true
        )
        planRepository.updatePref(planSortPreferences)

        val pref = getSortOrderFromPrefUseCase().first()

        assertEquals(PlanOrder.Name::class,pref::class)
        assertEquals(OrderType.Descending::class,pref.orderType::class)
    }

    @Test
    fun `Get Date Asc, correct order`() = runBlocking {
        val planSortPreferences = PlanSortPreferences(
            nameSelected = false,
            dateSelected = true,
            ascSelected = true,
            descSelected = false
        )
        planRepository.updatePref(planSortPreferences)

        val pref = getSortOrderFromPrefUseCase().first()

        assertEquals(PlanOrder.Date::class,pref::class)
        assertEquals(OrderType.Ascending::class,pref.orderType::class)
    }

    @Test
    fun `Get Date Desc, correct order`() = runBlocking {
        val planSortPreferences = PlanSortPreferences(
            nameSelected = false,
            dateSelected = true,
            ascSelected = false,
            descSelected = true
        )
        planRepository.updatePref(planSortPreferences)

        val pref = getSortOrderFromPrefUseCase().first()

        assertEquals(PlanOrder.Date::class,pref::class)
        assertEquals(OrderType.Descending::class,pref.orderType::class)
    }
}