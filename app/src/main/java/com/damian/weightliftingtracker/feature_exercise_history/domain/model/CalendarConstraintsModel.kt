package com.damian.weightliftingtracker.feature_exercise_history.domain.model

import java.util.*

data class CalendarConstraintsModel(
    //val years: List<Int>,
    //val monthsModel: List<MonthModel>,
    val dates : List<CalendarConstModel>,
    val startDate: Calendar,
    val endDate: Calendar,
    val previousDate: String
)
