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
fun SignUpScreen(onBack: () -> Unit, onSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    AppBackground {
        Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Create Account", style = MaterialTheme.typography.headlineMedium)
            BodyText("Join us to Organize. Study. Thrive.")
            Spacer(Modifier.height(16.dp))
            PillField(name, { name = it }, "Full Name")
            Spacer(Modifier.height(10.dp))
            PillField(email, { email = it }, "Email")
            Spacer(Modifier.height(10.dp))
            PillField(pass, { pass = it }, "Password", isPassword = true)
            Spacer(Modifier.height(10.dp))
            PillField(confirm, { confirm = it }, "Confirm Password", isPassword = true)
            Spacer(Modifier.height(16.dp))
            PinkCta("Sign Up", onClick = onSuccess)
            Spacer(Modifier.height(10.dp))
            GoogleButton(onClick = onSuccess)
            TextButton(onClick = onBack) { Text("Already have an account? Log in") }
        }
    }
}
