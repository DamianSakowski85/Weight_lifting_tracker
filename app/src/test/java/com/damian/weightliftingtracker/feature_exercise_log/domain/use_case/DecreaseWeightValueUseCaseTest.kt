package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DecreaseWeightValueUseCaseTest {

    private lateinit var decreaseWeightValueUseCase: DecreaseWeightValueUseCase

    @Before
    fun setup(){
        decreaseWeightValueUseCase = DecreaseWeightValueUseCase()
    }

    @Test(expected = InvalidWeightFormatException::class)
    fun invalidWeightFormatExceptionTest(){
        decreaseWeightValueUseCase("a")
    }

    @Test
    fun decreaseWeightValueTest(){
        val expectedValue = "50.0"
        val actualValue = decreaseWeightValueUseCase("50.5")
        Assert.assertEquals(expectedValue, actualValue)
    }
}