package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteExerciseUseCaseTest {

    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var exerciseToDelete : Exercise

    @Before
    fun setup() = runBlocking{
        exerciseRepository = FakeExerciseRepository()
        deleteExerciseUseCase = DeleteExerciseUseCase(exerciseRepository)
        exerciseToDelete = Exercise(
            1,
            1,
            "Bench Press",
            "desc"
        )
        exerciseRepository.insertExercise(exerciseToDelete)
    }

    @Test
    fun `Delete exercise, is deleted`() = runBlocking {
        Assert.assertEquals(exerciseToDelete,exerciseRepository.getExerciseById(1))
        deleteExerciseUseCase(exerciseToDelete)
        Assert.assertNull(exerciseRepository.getExerciseById(1))
    }
}