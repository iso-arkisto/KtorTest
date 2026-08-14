package com.yourname.ktortest.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yourname.ktortest.domain.repository.DatastoreOperations
import com.yourname.ktortest.utils.Constants.PREFERENCES_NAME
import com.yourname.ktortest.utils.Constants.PREFERENCES_KEY
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.yourname.ktortest.data.repository.DatastoreOperationsImpl.PreferencesKey.onboardingKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

class DatastoreOperationsImpl @Inject constructor(context: Context): DatastoreOperations {

    private object PreferencesKey {
        val onboardingKey = booleanPreferencesKey(PREFERENCES_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnboardingState(isCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[onboardingKey] = isCompleted
        }
    }

    override fun readOnboardingState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val onboardingState = preferences[onboardingKey] ?: false
            onboardingState
        }
    }
}