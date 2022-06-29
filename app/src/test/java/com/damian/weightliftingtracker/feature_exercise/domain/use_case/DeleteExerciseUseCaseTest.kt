package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteExerciseUseCaseTest {

    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase
    private lateinit var fakeExerciseRepository: FakeExerciseRepository
    private lateinit var exerciseToDelete : Exercise

    @Before
    fun setup() = runBlocking{
        fakeExerciseRepository = FakeExerciseRepository()
        deleteExerciseUseCase = DeleteExerciseUseCase(fakeExerciseRepository)
        exerciseToDelete = Exercise(
            1,
            1,
            "Bench Press",
            "desc"
        )
        fakeExerciseRepository.insertExercise(exerciseToDelete)
    }

    @Test
    fun `Delete exercise, is deleted`() = runBlocking {
        Assert.assertEquals(exerciseToDelete,fakeExerciseRepository.getExerciseById(1))
        deleteExerciseUseCase(exerciseToDelete)
        Assert.assertNull(fakeExerciseRepository.getExerciseById(1))
    }
}