package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.background.AppBackground
import com.keagan.finalapp.ui.theme.components.*

@Composable
fun LoginScreen(onBack: () -> Unit, onSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    AppBackground {
        Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Plan-demic", style = MaterialTheme.typography.headlineMedium)
            BodyText("Organize. Study. Thrive.")
            Spacer(Modifier.height(16.dp))
            PillField(email, { email = it }, "Email")
            Spacer(Modifier.height(10.dp))
            PillField(pass, { pass = it }, "Password", isPassword = true)
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Remember me")
                Text("Forgot password?")
            }
            Spacer(Modifier.height(16.dp))
            PinkCta("Log in", onClick = onSuccess)
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onBack) { Text("Sign up") }
            Spacer(Modifier.height(10.dp))
            GoogleButton(onClick = onSuccess)
        }
    }
}
