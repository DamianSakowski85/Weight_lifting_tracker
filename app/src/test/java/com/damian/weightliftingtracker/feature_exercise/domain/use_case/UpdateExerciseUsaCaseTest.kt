package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpdateExerciseUsaCaseTest {
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var updateExerciseUseCase: UpdateExerciseUseCase
    private lateinit var exercise: Exercise

    @Before
    fun setup() = runBlocking{
        exerciseRepository = FakeExerciseRepository()
        updateExerciseUseCase = UpdateExerciseUseCase(exerciseRepository)
        exercise = Exercise(
            1,
            1,
            "Bench Press",
            "desc"
        )
        exerciseRepository.insertExercise(exercise)
    }

    @Test
    fun `Update exercise test`() = runBlocking{
       val exerciseToUpdate = exercise.copy(
           exerciseName = "b",
           exerciseDescription = "b"
       )
        updateExerciseUseCase(exerciseToUpdate)

        val exerciseAfterUpdate = exerciseRepository.getExerciseById(1)
        Assert.assertEquals(exerciseToUpdate,exerciseAfterUpdate)
    }
}