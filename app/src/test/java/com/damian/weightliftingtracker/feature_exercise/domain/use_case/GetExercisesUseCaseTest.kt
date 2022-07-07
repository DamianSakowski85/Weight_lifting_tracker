package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetExercisesUseCaseTest {
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var getExercisesUseCase: GetExercisesUseCase

    @Before
    fun setup() = runBlocking{
        exerciseRepository = FakeExerciseRepository()
        getExercisesUseCase = GetExercisesUseCase(exerciseRepository)

        (1..5).forEachIndexed { index, c ->
            exerciseRepository.insertExercise(
                Exercise(
                    id = index,
                    1,
                    index.toString(),
                    index.toString()
                )
            )
        }
    }

    @Test
    fun `Get all exercises, 5 items`() = runBlocking{
        val exercises = getExercisesUseCase(1).first()

        Assert.assertEquals(5,exercises.size)
    }
}