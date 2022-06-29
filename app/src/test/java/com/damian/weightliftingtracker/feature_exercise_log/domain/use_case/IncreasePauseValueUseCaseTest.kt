package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IncreasePauseValueUseCaseTest {

    private lateinit var increasePauseValueUseCase: IncreasePauseValueUseCase

    @Before
    fun setup(){
        increasePauseValueUseCase = IncreasePauseValueUseCase()
    }

    @Test(expected = InvalidPauseFormatException::class)
    fun increasePauseValueExceptionTest(){
        increasePauseValueUseCase("a")
    }

    @Test
    fun increasePauseValueTest(){
        val expectedValue = "120"
        val actualValue = increasePauseValueUseCase("115")
        Assert.assertEquals(expectedValue,actualValue)
    }
}