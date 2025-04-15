package com.chrisroid.lostintravel.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthProvider @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            val authResult = firebaseAuth.signInWithCredential(com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)).await()
            Result.success(authResult.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}