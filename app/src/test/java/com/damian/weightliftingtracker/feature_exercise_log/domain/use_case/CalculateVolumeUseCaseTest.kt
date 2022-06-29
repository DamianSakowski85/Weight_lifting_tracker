package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.CalculateVolumeUseCase
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.LogVolume
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CalculateVolumeUseCaseTest {

    private lateinit var calculateVolumeUseCase : CalculateVolumeUseCase

    @Before
    fun setup(){
        calculateVolumeUseCase = CalculateVolumeUseCase()
    }

    @Test
    fun calculateVolumeTest() = runBlocking{
        val logsToInsert = mutableListOf<ExerciseLog>()
        (1..5).forEachIndexed { index, c ->
            logsToInsert.add(
               ExerciseLog(
                   index,
                   1,
                   8,
                   50.0,
                   120,
                   8,
                   50.0,
                   120,
                   LocalDate.now().toString()
               )
            )
        }
        val expectedVolume = LogVolume("2000.0","600")
        val calculatedVolume = calculateVolumeUseCase(logsToInsert)
        Assert.assertEquals(expectedVolume,calculatedVolume)
    }
}