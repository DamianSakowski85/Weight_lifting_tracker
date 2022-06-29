package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class PreviousExerciseLogsUseCaseTest {

    private lateinit var previousExerciseLogsUseCase: PreviousExerciseLogsUseCase
    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo

    @Before
    fun setup() = runBlocking{
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        previousExerciseLogsUseCase = PreviousExerciseLogsUseCase(fakeExerciseLogRepo)

        val log1 = ExerciseLog(
            1,
            1,
            8,
            50.0,
            120,
            8,
            50.0,
            120,
            LocalDate.now().minusDays(1).toString()
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
        fakeExerciseLogRepo.insert(log1)
        fakeExerciseLogRepo.insert(log2)
    }

    @Test
    fun getPreviousExerciseLogsTest() = runBlocking {
        val log = previousExerciseLogsUseCase(1,LocalDate.now().toString()).first()
        Assert.assertEquals(1,log.first().id)
    }
}