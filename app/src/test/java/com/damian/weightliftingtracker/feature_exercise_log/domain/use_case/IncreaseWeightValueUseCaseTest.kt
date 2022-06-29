package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IncreaseWeightValueUseCaseTest {

    private lateinit var increaseWeightValueUseCase: IncreaseWeightValueUseCase

    @Before
    fun setup(){
        increaseWeightValueUseCase = IncreaseWeightValueUseCase()
    }

    @Test(expected = InvalidWeightFormatException::class)
    fun increaseWeightValueExceptionTest(){
        increaseWeightValueUseCase("a")
    }

    @Test
    fun increaseWeightValueTest(){
        val expectedValue = "50.0"
        val actualValue =  increaseWeightValueUseCase("49.5")
        Assert.assertEquals(expectedValue,actualValue)
    }
}