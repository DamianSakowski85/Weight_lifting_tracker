package com.damian.weightliftingtracker.feature_exercise_history.domain.use_case

import com.damian.weightliftingtracker.feature_exercise_history.domain.model.CalendarConstModel
import com.damian.weightliftingtracker.feature_exercise_history.domain.model.CalendarConstraintsModel
import com.damian.weightliftingtracker.feature_exercise_history.domain.repository.ExerciseHistoryRepository
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class GetCalendarConstrainUseCase(
    private val exerciseHistoryRepository: ExerciseHistoryRepository
) {
    suspend operator fun invoke(exerciseId: Int): CalendarConstraintsModel? {
        val dates = exerciseHistoryRepository.getDates(exerciseId)

        val calendarConstList = ArrayList<CalendarConstModel>()

        if(dates.isNotEmpty()){
            val startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            val endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

            dates.forEach{
                val parsedDate = LocalDate.parse(it)
                val year = parsedDate.year
                val month = parsedDate.monthValue - 1
                val day = parsedDate.dayOfMonth

                calendarConstList.add(CalendarConstModel(year,month,day))

                if (it == dates.first()) {
                    startDate.set(year, month, day)
                }
                if (it == dates.last()) {
                    endDate.set(year, month, day)
                }
            }
            return CalendarConstraintsModel(calendarConstList, startDate, endDate, dates.last())
        }
        /*
        if (dates.isNotEmpty()) {
            val startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            val endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

            val years = ArrayList<Int>()
            val months = ArrayList<MonthModel>()

            dates.forEach {
                val parsedDate = LocalDate.parse(it)

                Log.i("test",parsedDate.dayOfMonth.toString())

                val year = parsedDate.year
                val month = parsedDate.monthValue - 1
                val day = parsedDate.dayOfMonth

                if (!years.contains(year)) {
                    years.add(year)
                }

                if (months.size == 0) {
                    val monthModel = MonthModel(month, days = ArrayList(day))
                    months.add(monthModel)
                }

                if (months.size != 0 && months.last().month == month) {
                    months.last().days.add(day)
                } else if (months.size != 0 && months.last().month != month) {
                    val monthModel = MonthModel(month, days = ArrayList(day))
                    months.add(monthModel)
                }

                if (it == dates.first()) {
                    startDate.set(year, month, day)
                }
                if (it == dates.last()) {
                    endDate.set(year, month, day)
                }
            }

            return CalendarConstraintsModel(years, months, startDate, endDate, dates.last())
        }

         */
        return null
    }
}