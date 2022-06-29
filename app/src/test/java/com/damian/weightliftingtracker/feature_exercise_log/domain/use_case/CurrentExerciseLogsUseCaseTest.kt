package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CurrentExerciseLogsUseCaseTest {

    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo
    private lateinit var currentExerciseLogsUseCase : CurrentExerciseLogsUseCase
    private val today = LocalDate.now().toString()
    private val yesterday = LocalDate.now().minusDays(1).toString()

    @Before
    fun setup() = runBlocking{
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        currentExerciseLogsUseCase = CurrentExerciseLogsUseCase(fakeExerciseLogRepo)

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
                    today
                )
            )
        }
        logsToInsert.forEach {
            fakeExerciseLogRepo.insert(it)
        }


        val otherLogs = mutableListOf<ExerciseLog>()
        (6..7).forEachIndexed { index, c ->
            otherLogs.add(
                ExerciseLog(
                    index,
                    2,
                    8,
                    50.0,
                    120,
                    8,
                    50.0,
                    120,
                    yesterday
                )
            )
        }
        otherLogs.forEach {
            fakeExerciseLogRepo.insert(it)
        }
    }

    @Test
    fun getCurrentLogsTest() = runBlocking {

        val logs = currentExerciseLogsUseCase(1,today).first()

        Assert.assertEquals(5,logs.size)
        Assert.assertEquals(1,logs.first().setNumber)
        Assert.assertEquals(5,logs.last().setNumber)
    }

    @Test
    fun getOtherLogs() = runBlocking {
        val logs = currentExerciseLogsUseCase(2,yesterday).first()

        Assert.assertEquals(2,logs.size)
        Assert.assertEquals(1,logs.first().setNumber)
        Assert.assertEquals(2,logs.last().setNumber)
    }
}