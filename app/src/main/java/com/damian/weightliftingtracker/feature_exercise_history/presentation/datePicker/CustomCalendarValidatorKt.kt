package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker

import com.damian.weightliftingtracker.feature_exercise_history.domain.model.CalendarConstModel
import com.google.android.material.datepicker.CalendarConstraints
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class CustomCalendarValidatorKt(
    //private val years: List<Int>,
    //private val monthModel: List<MonthModel>
private val dates : List<CalendarConstModel>
) : CalendarConstraints.DateValidator {

    override fun describeContents(): Int {
        return 0
    }

    override fun isValid(date: Long): Boolean {
        var isValidDays = false

        //Log.i("test",years.size.toString())
        //Log.i("test",monthModel.size.toString())
        //Log.i("test",monthModel.first().days.size.toString())

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

        /*
        years.forEach { year ->
            monthModel.forEach { monthModel ->
                monthModel.days.forEach { day ->
                    val calendarStart = Calendar.getInstance()
                    val calendarEnd = Calendar.getInstance()
                    val minDate = ArrayList<Long>()
                    val maxDate = ArrayList<Long>()

                   // Log.i("test",day.toString())
                    if (day == 1){
                        calendarStart.set(year, monthModel.month -1,day-1)
                        calendarEnd.set(year, monthModel.month,day)
                     //   Log.i("test","day = 1" + " " + monthModel.month)
                    }
                    else{
                        calendarStart.set(year, monthModel.month,day-1)
                        calendarEnd.set(year, monthModel.month,day)
                    }


                    minDate.add(calendarStart.timeInMillis)
                    maxDate.add(calendarEnd.timeInMillis)

                    isValidDays = isValidDays || !(minDate[0] > date || maxDate[0] < date)

                    //Log.i("test",year.toString() + " / " + monthModel.month.toString() + " / " + day.toString())
                }
            }
        }

         */

        return isValidDays
    }
}