package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.LogVolume
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CalculateVolumeUseCaseTest {

    private lateinit var calculateVolumeUseCase: CalculateVolumeUseCase
    private var logs = mutableListOf<ExerciseLog>()

    @Before
    fun setup() {
        calculateVolumeUseCase = CalculateVolumeUseCase()

        (1..2).forEachIndexed { index, c ->
            logs.add(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().toString()
                )
            )
        }
    }

    @Test
    fun calculateVolumeTest() = runBlocking{
        val volumeExpected = LogVolume(
            "880.0","360"
        )
        val volumeCalculated = calculateVolumeUseCase(logs)

        Assert.assertEquals(volumeExpected.weightVolume,volumeCalculated.weightVolume)
        Assert.assertEquals(volumeExpected.pauseVolume,volumeCalculated.pauseVolume)
    }
}