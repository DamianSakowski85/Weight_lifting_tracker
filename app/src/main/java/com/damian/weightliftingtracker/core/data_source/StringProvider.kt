package com.damian.weightliftingtracker.core.data_source

import android.content.Context
import androidx.annotation.StringRes

class StringProvider constructor(
    private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}