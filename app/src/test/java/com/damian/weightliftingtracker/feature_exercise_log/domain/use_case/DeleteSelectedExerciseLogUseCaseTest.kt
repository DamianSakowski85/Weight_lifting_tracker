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

class DeleteSelectedExerciseLogUseCaseTest {

    private lateinit var deleteSelectedExerciseLogUseCase: DeleteSelectedExerciseLogUseCase
    private lateinit var exerciseLogRepo: ExerciseLogRepository

    @Before
    fun setup() {
        exerciseLogRepo = FakeExerciseLogRepo()
        deleteSelectedExerciseLogUseCase = DeleteSelectedExerciseLogUseCase(exerciseLogRepo)
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
        exerciseLogRepo.insert(log)

        val logs = exerciseLogRepo.getLogs(1,LocalDate.now().toString()).first()
        Assert.assertEquals(1,logs.size)

        deleteSelectedExerciseLogUseCase(log)

        val logs1 = exerciseLogRepo.getLogs(1,LocalDate.now().toString()).first()
        Assert.assertEquals(0,logs1.size)
    }
}