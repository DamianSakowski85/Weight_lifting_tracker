package com.damian.weightliftingtracker.feature_plans.data.dataSource

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.damian.weightliftingtracker.feature_plans.data.data_source.PlanSortPreferences
import com.damian.weightliftingtracker.feature_plans.data.data_source.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class PreferencesManagerTest {
    val context : Context = ApplicationProvider.getApplicationContext()

    private lateinit var preferencesManagerTest: PreferencesManager

    @Before
    fun setUp() {
        preferencesManagerTest = PreferencesManager(context)
    }

    @Test
    fun writeDataTest() = runBlocking {
        preferencesManagerTest.updatePref(
            PlanSortPreferences(
                nameSelected = false,
                ascSelected = false,
                dateSelected = true,
                descSelected = true
            )
        )

        val result = preferencesManagerTest.planOrderPref.first()
        assertEquals(false,result.nameSelected)
        assertEquals(false,result.ascSelected)
        assertEquals(true,result.dateSelected)
        assertEquals(true,result.descSelected)
    }
}