package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_history.data.repository.FakeExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetCalendarConstraintsUseCaseTest {

    private lateinit var calendarConstrainUseCase: GetCalendarConstrainUseCase
    private lateinit var fakeExerciseHistoryRepository: FakeExerciseHistoryRepository

    @Before
    fun setup(){
        fakeExerciseHistoryRepository = FakeExerciseHistoryRepository()
        calendarConstrainUseCase = GetCalendarConstrainUseCase(fakeExerciseHistoryRepository)
    }

    @Test
    fun calendarConstraintsDaysTest(): Unit = runBlocking{
        val today : LocalDate = LocalDate.now()
        val yesterday : LocalDate = LocalDate.now()
        if (today.dayOfMonth == 1){
            today.minusDays(1)
            yesterday.minusDays(2)
        }
        else{
            yesterday.minusDays(1)
        }

        (1..2).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    yesterday.toString()
                )
            )
        }

        (3..5).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    today.toString()
                )
            )
        }

        val model = calendarConstrainUseCase(1)
        val dates = fakeExerciseHistoryRepository.getDates(1)

        //All dates contains the same year and month but different days
        val firstParsedDate = LocalDate.parse(dates.first())
        val secondParsedDate = LocalDate.parse(dates.last())

        val monthModel = model?.dates?.first()
        if (monthModel != null) {
           Assert.assertTrue(monthModel.day == firstParsedDate.dayOfMonth)
            //Problem with assertion,
            Assert.assertTrue(monthModel.day == secondParsedDate.dayOfMonth)
        }
    }

    @Test
    fun calendarConstraintsMonthsTest() = runBlocking {
        (1..2).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().minusDays(30).toString()
                )
            )
        }

        (3..5).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().toString()
                )
            )
        }

        val model = calendarConstrainUseCase(1)
        val dates = fakeExerciseHistoryRepository.getDates(1)

        Assert.assertTrue(dates.size == 2)
        //Assert.assertTrue(model?.monthsModel?.size == 2)

        val firstParsedDate = LocalDate.parse(dates.first())
        val secondParsedDate = LocalDate.parse(dates.last())

        if (model != null) {
            Assert.assertEquals(firstParsedDate.monthValue-1,model.dates.first().month)
            Assert.assertEquals(secondParsedDate.monthValue-1,model.dates.last().month)
        }
    }

    @Test
    fun calendarConstraintsYearsTest() = runBlocking {
        (1..2).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().minusDays(380).toString()
                )
            )
        }

        (3..5).forEachIndexed { index, c ->
            fakeExerciseHistoryRepository.addExerciseLogForTest(
                ExerciseLog(
                    id = index,
                    exerciseId = 1,
                    8,
                    55.0,
                    180,
                    8,
                    55.0,
                    180,
                    LocalDate.now().toString()
                )
            )
        }

        val model = calendarConstrainUseCase(1)
        val dates = fakeExerciseHistoryRepository.getDates(1)

        Assert.assertTrue(dates.size == 2)
        Assert.assertTrue(model?.dates?.size == 2)

        if (model != null) {
            Assert.assertEquals(2021,model.dates.first().year)
            Assert.assertEquals(2022,model.dates.last().year)
        }
    }
}