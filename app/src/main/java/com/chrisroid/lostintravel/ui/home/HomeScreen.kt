package com.chrisroid.lostintravel.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chrisroid.lostintravel.navigation.Screen
import com.chrisroid.lostintravel.viewmodel.AuthViewModel

@Composable
fun HomeScreen(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the Home Screen!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { authViewModel.signOut() }) {
            Text(text = "Sign Out")
        }
    }
}