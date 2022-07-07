package com.damian.weightliftingtracker.feature_exercise.domain.use_case

import com.damian.weightliftingtracker.feature_exercise.data.repository.FakeExerciseRepository
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.feature_exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetExerciseByIdUseCaseTest {

    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var getExerciseByIdUseCase: GetExerciseByIdUseCase
    private lateinit var exercise : Exercise

    @Before
    fun setup() = runBlocking{
        exerciseRepository = FakeExerciseRepository()
        getExerciseByIdUseCase = GetExerciseByIdUseCase(exerciseRepository)
        exercise = Exercise(
                1,
        1,
        "Bench Press",
        "desc"
        )
        exerciseRepository.insertExercise(exercise)
    }

    @Test
    fun `Get exercise by id test`() = runBlocking {
        val testExercise = getExerciseByIdUseCase(1)
        Assert.assertEquals(exercise,testExercise)
    }
}