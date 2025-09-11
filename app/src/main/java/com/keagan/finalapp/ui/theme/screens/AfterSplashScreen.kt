package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.background.AppBackground

@Composable
fun AfterSplashScreen(onLogin: () -> Unit, onSignUp: () -> Unit) {
    AppBackground {
        Column(Modifier.fillMaxSize().padding(24.dp)) {
            Text("Welcome")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onLogin) { Text("Login") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = onSignUp) { Text("Sign up") }
        }
    }
}
