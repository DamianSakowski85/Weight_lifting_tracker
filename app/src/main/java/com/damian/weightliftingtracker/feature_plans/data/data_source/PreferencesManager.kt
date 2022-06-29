package com.damian.weightliftingtracker.feature_plans.data.data_source

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TAG = "PreferencesManager"

data class PlanSortPreferences(
    val nameSelected: Boolean,
    val dateSelected: Boolean,
    val ascSelected: Boolean,
    val descSelected: Boolean
)

class PreferencesManager constructor(context: Context) {
    companion object {
        private const val APP_PREFERENCES = "app_preferences"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            APP_PREFERENCES
        )
        val NAME_SELECTED = booleanPreferencesKey("name_selected")
        val DATE_SELECTED = booleanPreferencesKey("date_selected")
        val ASC_SELECTED = booleanPreferencesKey("asc_selected")
        val DESC_SELECTED = booleanPreferencesKey("desc_selected")
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    val planOrderPref = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            return@map PlanSortPreferences(
                nameSelected = preferences[NAME_SELECTED] ?: true,
                dateSelected = preferences[DATE_SELECTED] ?: false,
                ascSelected = preferences[ASC_SELECTED] ?: true,
                descSelected = preferences[DESC_SELECTED] ?: false
            )
        }

    suspend fun updatePref(planSortPreferences: PlanSortPreferences) {
        dataStore.edit { preferences ->
            preferences[NAME_SELECTED] = planSortPreferences.nameSelected
            preferences[DATE_SELECTED] = planSortPreferences.dateSelected
            preferences[ASC_SELECTED] = planSortPreferences.ascSelected
            preferences[DESC_SELECTED] = planSortPreferences.descSelected
        }
    }
}