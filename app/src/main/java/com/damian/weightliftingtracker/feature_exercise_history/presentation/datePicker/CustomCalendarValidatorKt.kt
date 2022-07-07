package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker

import com.damian.weightliftingtracker.feature_exercise_history.domain.model.CalendarConstModel
import com.google.android.material.datepicker.CalendarConstraints
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class CustomCalendarValidatorKt(
private val dates : List<CalendarConstModel>
) : CalendarConstraints.DateValidator {

    override fun describeContents(): Int {
        return 0
    }

    override fun isValid(date: Long): Boolean {
        var isValidDays = false

        dates.forEach {
            val calendarStart = Calendar.getInstance()
            val calendarEnd = Calendar.getInstance()
            val minDate = ArrayList<Long>()
            val maxDate = ArrayList<Long>()

            calendarStart.set(it.year, it.month,it.day-1)
            calendarEnd.set(it.year, it.month,it.day)

            minDate.add(calendarStart.timeInMillis)
            maxDate.add(calendarEnd.timeInMillis)

            isValidDays = isValidDays || !(minDate[0] > date || maxDate[0] < date)
        }

        return isValidDays
    }
}