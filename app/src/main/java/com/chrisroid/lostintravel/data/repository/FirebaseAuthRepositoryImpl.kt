package com.chrisroid.lostintravel.data.repository

import com.chrisroid.lostintravel.data.local.UserPreferences
import com.chrisroid.lostintravel.data.remote.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val userPreferences: UserPreferences
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuthProvider.currentUser

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        val result = firebaseAuthProvider.signInWithGoogle(idToken)
        if (result.isSuccess) {
            userPreferences.saveLoginState(true)
        }
        return result
    }

    override suspend fun signOut(): Result<Unit> {
        val result = firebaseAuthProvider.signOut()
        if (result.isSuccess) {
            userPreferences.saveLoginState(false)
        }
        return result
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return userPreferences.loginState.first()
    }
}