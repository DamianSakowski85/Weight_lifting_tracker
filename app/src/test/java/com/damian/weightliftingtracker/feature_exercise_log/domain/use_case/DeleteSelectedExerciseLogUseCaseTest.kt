package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class DeleteSelectedExerciseLogUseCaseTest {

    private lateinit var deleteSelectedExerciseLogUseCase: DeleteSelectedExerciseLogUseCase
    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo

    @Before
    fun setup() {
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        deleteSelectedExerciseLogUseCase = DeleteSelectedExerciseLogUseCase(fakeExerciseLogRepo)
    }

    @Test
    fun deleteSelectedExerciseLogTest() = runBlocking {
        val log = ExerciseLog(
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
        fakeExerciseLogRepo.insert(log)

        val logs = fakeExerciseLogRepo.getLogs(1,LocalDate.now().toString()).first()
        Assert.assertEquals(1,logs.size)

        deleteSelectedExerciseLogUseCase(log)

        val logs1 = fakeExerciseLogRepo.getLogs(1,LocalDate.now().toString()).first()
        Assert.assertEquals(0,logs1.size)
    }
}