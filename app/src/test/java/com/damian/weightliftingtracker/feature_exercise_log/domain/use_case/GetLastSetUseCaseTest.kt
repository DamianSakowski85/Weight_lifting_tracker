package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.repository.ExerciseLogRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetLastSetUseCaseTest {

    private lateinit var getLastSetUseCase : GetLastSetUseCase
    private lateinit var exerciseLogRepo: ExerciseLogRepository

    @Before
    fun setup() = runBlocking{
        exerciseLogRepo = FakeExerciseLogRepo()
        getLastSetUseCase = GetLastSetUseCase(exerciseLogRepo)

        val log1 = ExerciseLog(
            1,
            1,
            8,
            50.0,
            120,
            8,
            50.0,
            120,
            LocalDate.now().toString()
        )

        val log2 = ExerciseLog(
            2,
            1,
            8,
            50.0,
            120,
            8,
            50.0,
            120,
            LocalDate.now().toString()
        )
        exerciseLogRepo.insert(log1)
        exerciseLogRepo.insert(log2)
    }

    @Test
    fun getLastSetTest() = runBlocking {
        val logs = exerciseLogRepo.getLogs(1,LocalDate.now().toString()).first()
        Assert.assertEquals(2,logs.size)

        val lastSet = getLastSetUseCase(1)

        Assert.assertEquals(logs.last(),lastSet)
    }
}