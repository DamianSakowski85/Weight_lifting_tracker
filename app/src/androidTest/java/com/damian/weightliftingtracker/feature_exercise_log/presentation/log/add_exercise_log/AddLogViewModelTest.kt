package com.damian.weightliftingtracker.feature_exercise_log.presentation.log.add_exercise_log

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.TestCoroutineRule
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_exercise_log.data.repository.FakeExerciseLogRepo
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.feature_exercise_log.domain.use_case.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class AddLogViewModelTest {

    private lateinit var fakeExerciseLogRepo: FakeExerciseLogRepo
    private lateinit var addLogViewModel: AddLogViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringProvider: StringProvider
    private lateinit var exerciseLogUseCases: ExerciseLogUseCases
    val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() = runBlocking{
        fakeExerciseLogRepo = FakeExerciseLogRepo()
        savedStateHandle = SavedStateHandle()

        savedStateHandle[STATE_KEY_EXERCISE_ID] = 1
        savedStateHandle[STATE_KEY_EXERCISE_NAME] = "Bench Press"

        val lastLog = ExerciseLog(
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
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )
        }
        Assert.assertEquals(1, addLogViewModel.addExerciseLogState.value.exerciseId)
        Assert.assertEquals("Bench Press",addLogViewModel.addExerciseLogState.value.exerciseName)
    }

    @Test
    fun onCloseEventTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnClose)

            val expectedEvent = AddLogViewModel.UiEvent.NavBack
            val actualEvent = addLogViewModel.eventFlow.first()

            Assert.assertEquals(expectedEvent,actualEvent)
        }
    }

    @Test
    fun updateWeightGoalTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightGoal("6"))
            Assert.assertEquals("6",addLogViewModel.addExerciseLogState.value.weightGoal)
            Assert.assertEquals("6",savedStateHandle[STATE_KEY_WEIGHT_GOAL])
        }
    }

    @Test
    fun updateWeightAchievedTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightAchieved("60.0"))
            Assert.assertEquals("60.0",addLogViewModel.addExerciseLogState.value.weightAchieved)
            Assert.assertEquals("60.0",savedStateHandle[STATE_KEY_WEIGHT_ACHIEVED])
        }
    }

    @Test
    fun onIncreaseWeightGoalValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightGoal("50.0"))
            addLogViewModel.onEvent(AddLogEvent.OnIncreaseWeightGoalValue)
            Assert.assertEquals("50.5",addLogViewModel.addExerciseLogState.value.weightGoal)
        }
    }
    @Test
    fun onDecreaseWeightGoalValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightGoal("50.0"))
            addLogViewModel.onEvent(AddLogEvent.OnDecreaseWeightGoalValue)
            Assert.assertEquals("49.5",addLogViewModel.addExerciseLogState.value.weightGoal)
        }
    }

    @Test
    fun onIncreaseRepsGoalValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterRepsGoal("8"))
            addLogViewModel.onEvent(AddLogEvent.OnIncreaseRepsGoalValue)
            Assert.assertEquals("9",addLogViewModel.addExerciseLogState.value.repsGoal)
        }
    }

    @Test
    fun onDecreaseRepsGoalValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterRepsGoal("8"))
            addLogViewModel.onEvent(AddLogEvent.OnDecreaseRepsGoalValue)
            Assert.assertEquals("7",addLogViewModel.addExerciseLogState.value.repsGoal)
        }
    }

    @Test
    fun onIncreasePauseGoalValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterPauseGoal("100"))
            addLogViewModel.onEvent(AddLogEvent.OnIncreasePauseGoalValue)
            Assert.assertEquals("105",addLogViewModel.addExerciseLogState.value.pauseGoal)
        }
    }
    @Test
    fun onDecreasePauseGoalValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterPauseGoal("100"))
            addLogViewModel.onEvent(AddLogEvent.OnDecreasePauseGoalValue)
            Assert.assertEquals("95",addLogViewModel.addExerciseLogState.value.pauseGoal)
        }
    }

    //Achieved
    @Test
    fun onIncreaseWeightAchievedValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightAchieved("50.0"))
            addLogViewModel.onEvent(AddLogEvent.OnIncreaseWeightAchievedValue)
            Assert.assertEquals("50.5",addLogViewModel.addExerciseLogState.value.weightAchieved)
        }
    }
    @Test
    fun onDecreaseWeightAchievedValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightAchieved("50.0"))
            addLogViewModel.onEvent(AddLogEvent.OnDecreaseWeightAchievedValue)
            Assert.assertEquals("49.5",addLogViewModel.addExerciseLogState.value.weightAchieved)
        }
    }

    @Test
    fun onIncreaseRepsAchievedValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterRepsAchieved("8"))
            addLogViewModel.onEvent(AddLogEvent.OnIncreaseRepsAchievedValue)
            Assert.assertEquals("9",addLogViewModel.addExerciseLogState.value.repsAchieved)
        }
    }

    @Test
    fun onDecreaseRepsAchievedValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterRepsAchieved("8"))
            addLogViewModel.onEvent(AddLogEvent.OnDecreaseRepsAchievedValue)
            Assert.assertEquals("7",addLogViewModel.addExerciseLogState.value.repsAchieved)
        }
    }

    @Test
    fun onIncreasePauseAchievedValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterPauseAchieved("100"))
            addLogViewModel.onEvent(AddLogEvent.OnIncreasePauseAchievedValue)
            Assert.assertEquals("105",addLogViewModel.addExerciseLogState.value.pauseAchieved)
        }
    }
    @Test
    fun onDecreasePauseAchievedValueTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )

            addLogViewModel.onEvent(AddLogEvent.OnEnterPauseAchieved("100"))
            addLogViewModel.onEvent(AddLogEvent.OnDecreasePauseAchievedValue)
            Assert.assertEquals("95",addLogViewModel.addExerciseLogState.value.pauseAchieved)
        }
    }

    @Test
    fun saveLogTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )
            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightGoal("50.0"))
            addLogViewModel.onEvent(AddLogEvent.OnEnterRepsGoal("5"))
            addLogViewModel.onEvent(AddLogEvent.OnEnterPauseGoal("100"))

            addLogViewModel.onEvent(AddLogEvent.OnSave)

            val expectedEvent = AddLogViewModel.UiEvent.ShowMessage(context.getString(R.string.set_added))
            val actualEvent = addLogViewModel.eventFlow.first()

            val weightAchievedValue = 50.0
            val insertedLog = fakeExerciseLogRepo.getLogs(1,LocalDate.now().toString()).first()

            Assert.assertEquals(expectedEvent,actualEvent)
            Assert.assertEquals(weightAchievedValue,insertedLog.last().weightAchieved, 0.0)
        }
    }

    @Test
    fun saveLogExceptionMessageTest(){
        testCoroutineRule.runBlockingTest {
            addLogViewModel = AddLogViewModel(
                savedStateHandle,
                stringProvider,
                exerciseLogUseCases
            )
            addLogViewModel.onEvent(AddLogEvent.OnEnterWeightGoal("a"))
            addLogViewModel.onEvent(AddLogEvent.OnEnterRepsGoal("b"))
            addLogViewModel.onEvent(AddLogEvent.OnEnterPauseGoal("c"))

            addLogViewModel.onEvent(AddLogEvent.OnSave)

            val expectedEvent = AddLogViewModel.UiEvent.ShowMessage::class
            val actualEvent = addLogViewModel.eventFlow.first()::class
            Assert.assertEquals(expectedEvent,actualEvent)
        }
    }
}