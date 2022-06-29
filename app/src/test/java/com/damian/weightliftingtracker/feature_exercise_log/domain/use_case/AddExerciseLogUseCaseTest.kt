package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.InvalidExerciseLogException
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class AddExerciseLogUseCaseTest {

    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo
    private lateinit var addExerciseLogUseCase: AddExerciseLogUseCase

    @Before
    fun setup(){
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        addExerciseLogUseCase = AddExerciseLogUseCase(fakeExerciseLogRepo)
    }

    @Test(expected = InvalidExerciseLogException::class)
    fun emptyWeightExceptionTest(): Unit = runBlocking{
        addExerciseLogUseCase(
            1,
            "",
            "8",
            "120",
            "",
            "8",
            "120",
            LocalDate.now().toString())
    }
    @Test(expected = InvalidExerciseLogException::class)
    fun emptyRepsExceptionTest(): Unit = runBlocking{
        addExerciseLogUseCase(
            1,
            "40.0",
            "",
            "120",
            "40.0",
            "",
            "120",
            LocalDate.now().toString())
    }
    @Test(expected = InvalidExerciseLogException::class)
    fun emptyPauseExceptionTest(): Unit = runBlocking{
        addExerciseLogUseCase(
            1,
            "40.0",
            "8",
            "",
            "40.0",
            "8",
            "",
            LocalDate.now().toString())
    }
    @Test
    fun insertExerciseLogTest(): Unit = runBlocking{
        val exerciseLog = ExerciseLog(
            0,
            1,
            8,
            40.0,
            120,
            8,
            40.0,
            120,
            LocalDate.now().toString()
        )
        addExerciseLogUseCase(
            1,
            "40.0",
            "8",
            "120",
            "40.0",
            "8",
            "120",
            LocalDate.now().toString()
        )
        Assert.assertEquals(exerciseLog,fakeExerciseLogRepo.getLastSet(1))
    }
}