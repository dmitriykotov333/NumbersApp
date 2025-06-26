package com.kotdev.numbersapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Singleton
class FilterPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "FILTER_DATA_STORE")
        private val SELECTED_TYPES = stringSetPreferencesKey("selected_types")
    }

    private val dataStore = context.dataStore

    suspend fun saveSelectedTypes(types: Set<String>) {
        dataStore.edit { preferences ->
            preferences[SELECTED_TYPES] = types
        }
    }

    fun getSelectedTypes(): Flow<Set<String>> = dataStore.data
        .map { preferences -> preferences[SELECTED_TYPES] ?: emptySet() }

    suspend fun getFirst(): Set<String> = dataStore.data
        .map { preferences -> preferences[SELECTED_TYPES] ?: emptySet() }.first()
}