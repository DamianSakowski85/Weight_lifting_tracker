package com.damian.weightliftingtracker.feature_plans.domain.use_case

import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.data.repository.FakePlanRepository
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.repository.PlanRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test

class GetPlansUseCaseTest{

    private lateinit var getPlansUseCase: GetPlansUseCase
    private lateinit var planaRepository: PlanRepository

    @Before
    fun setup(){
        planaRepository = FakePlanRepository()
        getPlansUseCase = GetPlansUseCase(planaRepository)

        val plansToInsert = mutableListOf<Plan>()
        ('a'..'z').forEachIndexed { index, c ->
            plansToInsert.add(
                Plan(
                    id = index,
                    planName = c.toString(),
                    planDescription = "$c desc",
                    timestamp = index.toLong()
                )
            )
        }
        plansToInsert.shuffle()

        runBlocking {
            plansToInsert.forEach {
                planaRepository.insertPlan(it)
            }
        }
    }

    @Test
    fun `Order plans by name asc, correct order`() = runBlocking{
        planaRepository.updatePref(PlanSortPreferences(
            nameSelected = true,
            dateSelected = false,
            ascSelected = true,
            descSelected = false
        ))
        val plans = getPlansUseCase().first()

        for (i in 0..plans.size - 2){
            assertThat(plans[i].planName).isLessThan(plans[i+1].planName)
        }
    }

    @Test
    fun `Order plans by name desc, correct order`() = runBlocking{
        planaRepository.updatePref(PlanSortPreferences(
            nameSelected = true,
            dateSelected = false,
            ascSelected = false,
            descSelected = true
        ))
        val plans = getPlansUseCase().first()

        for (i in 0..plans.size - 2){
            assertThat(plans[i].planName).isGreaterThan(plans[i+1].planName)
        }
    }

    @Test
    fun `Order plans by date asc, correct order`() = runBlocking{
        planaRepository.updatePref(PlanSortPreferences(
            nameSelected = false,
            dateSelected = true,
            ascSelected = true,
            descSelected = false
        ))
        val plans = getPlansUseCase().first()

        for (i in 0..plans.size - 2){
            assertThat(plans[i].timestamp).isLessThan(plans[i+1].timestamp)
        }
    }

    @Test
    fun `Order plans by date desc, correct order`() = runBlocking{
        planaRepository.updatePref(PlanSortPreferences(
            nameSelected = false,
            dateSelected = true,
            ascSelected = false,
            descSelected = true
        ))
        val plans = getPlansUseCase().first()

        for (i in 0..plans.size - 2){
            assertThat(plans[i].timestamp).isGreaterThan(plans[i+1].timestamp)
        }
    }
}