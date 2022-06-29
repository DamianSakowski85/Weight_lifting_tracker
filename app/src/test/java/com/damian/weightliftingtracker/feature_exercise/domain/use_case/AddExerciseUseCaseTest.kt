package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.model.InvalidExerciseException
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddExerciseUseCaseTest {
    private lateinit var addExerciseUseCase: AddExerciseUseCase
    private lateinit var fakeExerciseRepository: FakeExerciseRepository

    @Before
    fun setup() {
        fakeExerciseRepository = FakeExerciseRepository()
        addExerciseUseCase = AddExerciseUseCase(fakeExerciseRepository)
    }

    @Test(expected = InvalidExerciseException::class)
    fun `Insert exercise with blank name, exception test`() = runBlocking {
        val exercise = Exercise(
            1,
            1,
            "",
            ""
        )
        addExerciseUseCase(exercise)
    }

    @Test
    fun `Insert exercise, is inserted`() = runBlocking {
        val exercise = Exercise(
            1,
            1,
            "exercise a",
            "desc of exercise a"
        )
        addExerciseUseCase(exercise)

        Assert.assertEquals(exercise, fakeExerciseRepository.getExerciseById(1))
    }
}