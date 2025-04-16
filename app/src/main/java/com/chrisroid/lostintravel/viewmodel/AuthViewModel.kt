package com.chrisroid.lostintravel.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisroid.lostintravel.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

//    private val _user = MutableStateFlow<FirebaseUser>()
//    val user : StateFlow<FirebaseUser> = _user


    // Add this function to handle errors
    fun handleSignInError(errorMessage: String) {
        _authState.value = AuthState.Error(errorMessage)
    }

    fun signInWithGoogle(idToken: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                // Firebase sign-in
                val result = repository.signInWithGoogle(idToken)

                // GraphQL login with token
                val apiToken = repository.googleLogin(idToken)
                if (apiToken.isNullOrBlank()) throw Exception("Google GraphQL login failed")

                // Save token to DataStore
                repository.saveAuthState(true, apiToken)

                _authState.value = AuthState.Success(result.user)
                _isLoggedIn.value = true
            } catch (e: Exception) {
                handleSignInError(e.message ?: "Authentication failed")
                _isLoggedIn.value = false
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
            repository.saveAuthState(false, "")
            _isLoggedIn.value = false
            _authState.value = AuthState.Idle
        }
    }

    fun loginWithEmail(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val token = repository.loginWithEmail(email, password)
                repository.saveAuthState(true, token)
                _authState.value = AuthState.Success(null)
                _isLoggedIn.value = true
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
                _isLoggedIn.value = false
            }
        }
    }

    fun signUp(name: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val success = repository.createAccount(email, password, name)
                if (success) {
                    loginWithEmail(email, password)
                } else {
                    _authState.value = AuthState.Error("Account creation failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Signup failed")
            }
        }
    }

}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
}