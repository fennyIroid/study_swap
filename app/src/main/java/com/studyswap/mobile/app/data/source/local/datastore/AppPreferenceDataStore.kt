package com.studyswap.mobile.app.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.studyswap.mobile.app.data.source.Constants.DataStore.PREFERENCE_NAME
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferenceDataStore @Inject constructor(
    private val applicationContext: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCE_NAME,
        corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() }
    )

    private object Keys {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    suspend fun getUserToken(): String? {
        return applicationContext.dataStore.data.map { it[Keys.USER_TOKEN] }.first()
    }

    suspend fun saveUserToken(token: String) {
        applicationContext.dataStore.edit { it[Keys.USER_TOKEN] = token }
    }

    suspend fun clearAll() {
        applicationContext.dataStore.edit { it.clear() }
    }
}
