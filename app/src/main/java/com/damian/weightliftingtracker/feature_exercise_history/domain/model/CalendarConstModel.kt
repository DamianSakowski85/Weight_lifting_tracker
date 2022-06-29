package com.damian.weightliftingtracker.feature_exercise_history.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarConstModel(
    val year: Int,
    val month : Int,
    val day : Int
): Parcelable