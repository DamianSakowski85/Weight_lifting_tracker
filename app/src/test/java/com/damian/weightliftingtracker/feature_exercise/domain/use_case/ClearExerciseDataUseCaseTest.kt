package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ClearExerciseDataUseCaseTest {

    private lateinit var clearExerciseDataUseCase: ClearExerciseDataUseCase
    private lateinit var fakeExerciseRepository: FakeExerciseRepository

    @Before
    fun setup() = runBlocking{
        fakeExerciseRepository = FakeExerciseRepository()
        clearExerciseDataUseCase = ClearExerciseDataUseCase(fakeExerciseRepository)

        (1..5).forEachIndexed { index, c ->
            fakeExerciseRepository.addExerciseLogsForTest(
                ExerciseLog(
                    id = index,
                    1,
                    8,
                    50.0,
                    180,
                    8,
                    50.0,
                    180,
                    c.toString()
                )
            )
        }
    }

    @Test
    fun `clear exercise data, is cleared`() = runBlocking {
        Assert.assertEquals(5,fakeExerciseRepository.getExerciseLogsData().size)

        clearExerciseDataUseCase.invoke(1)

        Assert.assertEquals(0,fakeExerciseRepository.getExerciseLogsData().size)
    }
}