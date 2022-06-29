package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DecreasePauseValueUseCaseTest {

    private lateinit var decreasePauseValueUseCase: DecreasePauseValueUseCase

    @Before
    fun setup(){
        decreasePauseValueUseCase = DecreasePauseValueUseCase()
    }

    @Test(expected = InvalidPauseFormatException::class)
    fun invalidPauseFormatExceptionTest(){
        decreasePauseValueUseCase("1.1")
    }

    @Test
    fun decreasePauseValueTest(){
        val expectedValue = "20"
        val actualValue = decreasePauseValueUseCase("25")
        Assert.assertEquals(expectedValue,actualValue)
    }
}