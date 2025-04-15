package com.chrisroid.lostintravel.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chrisroid.lostintravel.MainActivity
import com.chrisroid.lostintravel.navigation.Screen
import com.chrisroid.lostintravel.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current as? MainActivity

    LaunchedEffect(key1 = uiState.isUserLoggedIn) {
        if (uiState.isUserLoggedIn) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { context?.initiateGoogleSignIn() }) {
            Text(text = "Continue with Google")
        }

        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = uiState.errorMessage!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Loading...") // Replace with a proper loading indicator
        }
    }
}