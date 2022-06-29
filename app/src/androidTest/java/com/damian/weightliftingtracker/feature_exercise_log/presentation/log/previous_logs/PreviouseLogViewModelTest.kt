package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.previous_logs

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.*
import com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.STATE_KEY_EXERCISE_NAME
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class PreviouseLogViewModelTest {

    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo
    private lateinit var previousLogsViewModel: PreviousLogsViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringProvider: StringProvider
    private lateinit var exerciseLogUseCases: ExerciseLogUseCases
    val context: Context = ApplicationProvider.getApplicationContext()

    private val firstLog = ExerciseLog(
        1,
        1,
        8,
        50.0,
        120,
        8,
        50.0,
        120,
        LocalDate.now().minusDays(1).toString()
    )

    private val lastLog = ExerciseLog(
        2,
        1,
        8,
        50.0,
        120,
        8,
        50.0,
        120,
        LocalDate.now().toString()
    )

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() = runBlocking{
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        savedStateHandle = SavedStateHandle()

        savedStateHandle[com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log.STATE_KEY_EXERCISE_ID] = 1
        savedStateHandle[STATE_KEY_EXERCISE_NAME] = "Bench Press"

        fakeExerciseLogRepo.insert(firstLog)
        fakeExerciseLogRepo.insert(lastLog)

        stringProvider = StringProvider(context)
        exerciseLogUseCases = ExerciseLogUseCases(
            addExerciseLogUseCase = AddExerciseLogUseCase(fakeExerciseLogRepo),
            calculateVolumeUseCase = CalculateVolumeUseCase(),
            currentExerciseLogsUseCase = CurrentExerciseLogsUseCase(fakeExerciseLogRepo),
            decreasePauseValueUseCase = DecreasePauseValueUseCase(),
            decreaseRepsValueUseCase = DecreaseRepsValueUseCase(),
            decreaseWeightValueUseCase = DecreaseWeightValueUseCase(),
            deleteSelectedExerciseLogUseCase = DeleteSelectedExerciseLogUseCase(fakeExerciseLogRepo),
            getLastSetUseCase = GetLastSetUseCase(fakeExerciseLogRepo),
            increasePauseValueUseCase = IncreasePauseValueUseCase(),
            increaseRepsValueUseCase = IncreaseRepsValueUseCase(),
            increaseWeightValueUseCase = IncreaseWeightValueUseCase(),
            previousExerciseLogsUseCase = PreviousExerciseLogsUseCase(fakeExerciseLogRepo)
        )
    }

    @Test
    fun viewModelInitTest(){
        testCoroutineRule.runBlockingTest {
            previousLogsViewModel = PreviousLogsViewModel(
                savedStateHandle,
                exerciseLogUseCases
            )
        }
        Assert.assertEquals(1,previousLogsViewModel.previousLogsState.value.logs.last().id)
    }
}