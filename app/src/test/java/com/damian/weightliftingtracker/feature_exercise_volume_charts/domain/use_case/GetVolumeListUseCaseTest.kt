package com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository.FakeExerciseVolumeRepo
import com.madrapps.plot.line.DataPoint
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetVolumeListUseCaseTest {

    private lateinit var getVolumeListUseCase: GetVolumeListUseCase
    private lateinit var fakeExerciseVolumeRepo: FakeExerciseVolumeRepo

    private val testExerciseLog = ExerciseLog(
        id = 1,
        exerciseId = 1,
        8,
        55.0,
        180,
        8,
        55.0,
        180,
        LocalDate.now().minusDays(1).toString()
    )
    private val secondTestExerciseLog = ExerciseLog(
        id = 2,
        exerciseId = 1,
        8,
        55.0,
        180,
        6,
        55.0,
        100,
        LocalDate.now().toString()
    )

    @Before
    fun setup(){
        fakeExerciseVolumeRepo = FakeExerciseVolumeRepo()
        fakeExerciseVolumeRepo.addExerciseLogForTest(testExerciseLog)
        fakeExerciseVolumeRepo.addExerciseLogForTest(secondTestExerciseLog)

        getVolumeListUseCase = GetVolumeListUseCase(fakeExerciseVolumeRepo)
    }

    @Test
    fun volumeDataTest() = runBlocking{
        val data = getVolumeListUseCase(1)

        val xFirstIndex = 1
        val yFirstValue = 440.0
        val firstDataPoint = DataPoint(xFirstIndex.toFloat(),yFirstValue.toFloat())
        Assert.assertEquals(firstDataPoint,data[0][1])

        val xSecondIndex = 2
        val ySecondValue = 330.0
        val secondDataPoint = DataPoint(xSecondIndex.toFloat(),ySecondValue.toFloat())
        Assert.assertEquals(secondDataPoint,data[0][2])

        val firstPauseValue = 180
        val firstPauseDataPoint = DataPoint(xFirstIndex.toFloat(),firstPauseValue.toFloat())
        Assert.assertEquals(firstPauseDataPoint,data[1][1])

        val secondPauseValue = 100
        val secondPauseDataPoint = DataPoint(xSecondIndex.toFloat(),secondPauseValue.toFloat())
        Assert.assertEquals(secondPauseDataPoint,data[1][2])
    }
}