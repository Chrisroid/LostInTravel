package com.chrisroid.lostintravel.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private val loginStateKey = booleanPreferencesKey("login_state")

    val loginState: Flow<Boolean> = context.userDataStore.data
        .map { preferences ->
            preferences[loginStateKey] ?: false
        }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[loginStateKey] = isLoggedIn
        }
    }
}