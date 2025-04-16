package com.chrisroid.lostintravel.data.loacal

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

// PreferencesKeys.kt
object PreferencesKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    val TOKEN = stringPreferencesKey("token")
    // Add other keys as needed
}