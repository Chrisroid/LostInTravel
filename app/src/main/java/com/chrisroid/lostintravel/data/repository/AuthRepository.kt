package com.chrisroid.lostintravel.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>
    suspend fun signOut(): Result<Unit>
    suspend fun isUserLoggedIn(): Boolean
}