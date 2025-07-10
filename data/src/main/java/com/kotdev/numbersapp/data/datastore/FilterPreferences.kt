package com.kotdev.numbersapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kotdev.numbersapp.core.utils.Utils
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Singleton
class FilterPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = Utils.FILTER_DATA_STORE)
        private val SELECTED_TYPES = stringSetPreferencesKey(name = Utils.SELECTED_TYPES)
        private val SORT_DESCENDING = booleanPreferencesKey(name = Utils.SORT_DESCENDING)
    }

    private val dataStore = context.dataStore

    suspend fun saveSelectedTypes(types: Set<String>) {
        dataStore.edit { preferences ->
            preferences[SELECTED_TYPES] = types
        }
    }

    suspend fun saveSort(sortDescending: Boolean): Boolean {
        dataStore.edit { preferences ->
            if (sortDescending) {
                preferences[SORT_DESCENDING] = true
            } else {
                preferences[SORT_DESCENDING] = false
            }
        }
        return sortDescending
    }

    fun getSortDescending(): Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SORT_DESCENDING] ?: true
        }

    fun getSelectedTypes(): Flow<Set<String>> = dataStore.data
        .map { preferences ->
            preferences[SELECTED_TYPES] ?: setOf(
                TypeRequest.MATH.name,
                TypeRequest.TRIVIA.name,
                TypeRequest.YEAR.name,
                TypeRequest.DATE.name
            )
        }

    suspend fun getFirst(): Set<String> = dataStore.data
        .map { preferences -> preferences[SELECTED_TYPES] ?: emptySet() }.first()
}