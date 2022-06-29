package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_history.data.repository.FakeExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class LoadLogsUseCaseTest {

    private lateinit var loadLogUseCase: LoadLogUseCase
    private lateinit var fakeExerciseHistoryRepository: FakeExerciseHistoryRepository

    @Before
    fun setup(){
        fakeExerciseHistoryRepository = FakeExerciseHistoryRepository()
        (1..3).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
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

        (4..5).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().minusDays(1).toString()
                )
            )
        }

        loadLogUseCase = LoadLogUseCase(fakeExerciseHistoryRepository)
    }

    @Test
    fun getLogsWithDateTest() = runBlocking{
        val logs = loadLogUseCase(1,LocalDate.now().toString()).first()
        Assert.assertEquals(3,logs.size)
    }

    @Test
    fun getLogsWithoutDateTest() = runBlocking{
        val logs = loadLogUseCase(1,null).first()
        Assert.assertEquals(2,logs.size)
    }
}