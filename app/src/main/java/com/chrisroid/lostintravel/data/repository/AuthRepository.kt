package com.chrisroid.lostintravel.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.apollographql.apollo3.ApolloClient
import com.chrisroid.lostintravel.CreateNewUserMutation
import com.chrisroid.lostintravel.GetRecommendedPlacesQuery
import com.chrisroid.lostintravel.GoogleLoginMutation
import com.chrisroid.lostintravel.LoginMutation
import com.chrisroid.lostintravel.data.loacal.PreferencesKeys
import com.chrisroid.lostintravel.type.CreateUser
import com.chrisroid.lostintravel.type.LocationInput
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStore: DataStore<Preferences>,
    private val apolloClient: ApolloClient
) {
    suspend fun signInWithGoogle(idToken: String): AuthResult {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return firebaseAuth.signInWithCredential(credential).await()
    }

    suspend fun saveAuthState(isLoggedIn: Boolean, token : String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    fun getAuthState(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
            }
    }

    fun getToken(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.TOKEN] ?: ""
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    suspend fun getRecommendedPlaces(): List<GetRecommendedPlacesQuery.RecommendedPlace?>? {
        return apolloClient.query(GetRecommendedPlacesQuery()).execute().data?.RecommendedPlaces
    }

    suspend fun googleLogin(googleIdToken : String): String?{
        val response = apolloClient
            .mutation(GoogleLoginMutation(access_token = googleIdToken))
            .execute()

        return response.data?.GoogleLogin?.token
    }
    suspend fun loginWithEmail(email: String, password: String): String {
        val response = apolloClient
            .mutation(LoginMutation(email = email, password = password))
            .execute()

        return response.data?.Login?.token ?: throw Exception("Login failed")
    }

    suspend fun createAccount(email: String, password: String, name: String): Boolean {
        val response = apolloClient
            .mutation(
                CreateNewUserMutation(
                input = CreateUser(
                    email = email,
                    password = password,
                    full_name = name,
                    location = LocationInput(latitude = "0", longitude = "0")
                )
            )
            )
            .execute()

        return response.data?.CreateNewUser?.email != null
    }


}