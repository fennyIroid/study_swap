package com.studyswap.mobile.app.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.studyswap.mobile.app.data.source.Constants.DataStore.PREFERENCE_NAME
import com.studyswap.mobile.app.data.source.remote.model.UserData
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
        val USER_ID = intPreferencesKey("user_id")
        val FULL_NAME = stringPreferencesKey("full_name")
        val EMAIL = stringPreferencesKey("email")
        val HANDLE = stringPreferencesKey("handle")
        val UNIVERSITY = stringPreferencesKey("university")
        val MAJOR = stringPreferencesKey("major")
        val UPLOADS = intPreferencesKey("uploads")
        val RATING = doublePreferencesKey("rating")
        val REPUTATION = intPreferencesKey("reputation")
        val PROFILE_IMAGE = stringPreferencesKey("profile_image")
    }

    suspend fun getUserToken(): String? {
        return applicationContext.dataStore.data.map { it[Keys.USER_TOKEN] }.first()
    }

    suspend fun saveUserToken(token: String) {
        applicationContext.dataStore.edit { it[Keys.USER_TOKEN] = token }
    }

    suspend fun saveUserData(userData: UserData) {
        applicationContext.dataStore.edit { prefs ->
            userData.id?.let { prefs[Keys.USER_ID] = it }
            userData.fullName?.let { prefs[Keys.FULL_NAME] = it }
            userData.email?.let { prefs[Keys.EMAIL] = it }
            userData.handle?.let { prefs[Keys.HANDLE] = it }
            userData.university?.let { prefs[Keys.UNIVERSITY] = it }
            userData.major?.let { prefs[Keys.MAJOR] = it }
            userData.uploads?.let { prefs[Keys.UPLOADS] = it }
            userData.rating?.let { prefs[Keys.RATING] = it }
            userData.reputation?.let { prefs[Keys.REPUTATION] = it }
            userData.profileImage?.let { prefs[Keys.PROFILE_IMAGE] = it }
        }
    }

    suspend fun getUserData(): UserData? {
        val data = applicationContext.dataStore.data.first()
        if (data[Keys.USER_ID] == null) return null
        
        return UserData(
            id = data[Keys.USER_ID],
            fullName = data[Keys.FULL_NAME],
            email = data[Keys.EMAIL],
            handle = data[Keys.HANDLE],
            university = data[Keys.UNIVERSITY],
            major = data[Keys.MAJOR],
            uploads = data[Keys.UPLOADS],
            rating = data[Keys.RATING],
            reputation = data[Keys.REPUTATION],
            profileImage = data[Keys.PROFILE_IMAGE]
        )
    }

    suspend fun clearAll() {
        applicationContext.dataStore.edit { it.clear() }
    }
}
