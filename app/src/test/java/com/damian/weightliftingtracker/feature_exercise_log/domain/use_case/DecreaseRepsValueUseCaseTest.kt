package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DecreaseRepsValueUseCaseTest {

    private lateinit var decreaseRepsValueUseCase: DecreaseRepsValueUseCase

    @Before
    fun setup(){
        decreaseRepsValueUseCase = DecreaseRepsValueUseCase()
    }

    @Test(expected = InvalidRepsFormatException::class)
    fun decreaseRepsValueExceptionTest(){
        decreaseRepsValueUseCase("a")
    }

    @Test
    fun decreaseRepsValueTest(){
        val expectedValue = "8"
        val actualValue = decreaseRepsValueUseCase("9")
        Assert.assertEquals(expectedValue,actualValue)
    }
}