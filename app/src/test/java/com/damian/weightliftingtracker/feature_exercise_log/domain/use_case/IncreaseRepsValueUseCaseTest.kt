package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IncreaseRepsValueUseCaseTest {

    private lateinit var increaseRepsValueUseCase: IncreaseRepsValueUseCase

    @Before
    fun setup(){
        increaseRepsValueUseCase = IncreaseRepsValueUseCase()
    }

    @Test(expected = InvalidRepsFormatException::class)
    fun increaseRepsValueExceptionTest(){
        increaseRepsValueUseCase("a")
    }

    @Test
    fun increaseRepsValueTest(){
        val expectedValue = "5"
        val actualValue = increaseRepsValueUseCase("4")
        Assert.assertEquals(expectedValue,actualValue)
    }
}