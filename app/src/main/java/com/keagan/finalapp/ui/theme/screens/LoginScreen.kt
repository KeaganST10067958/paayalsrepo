package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.background.AppBackground
import com.keagan.finalapp.ui.theme.components.*

@Composable
fun LoginScreen(
    onBack: () -> Unit,                 // <-- rename
    onSuccess: () -> Unit               // <-- rename
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    AppBackground {
        Column(Modifier.fillMaxSize().padding(24.dp)) {
            BodyText("Login to continue")
            Spacer(Modifier.height(12.dp))
            PillField(value = email, onValueChange = { email = it }, placeholder = "Email")
            Spacer(Modifier.height(8.dp))
            PillField(value = pass, onValueChange = { pass = it }, placeholder = "Password", isPassword = true)
            Spacer(Modifier.height(16.dp))
            PinkCta("Login", onClick = onSuccess)
            Spacer(Modifier.height(8.dp))
            WhiteCta("Back", onClick = onBack)
            Spacer(Modifier.height(8.dp))
            GoogleButton(onClick = onSuccess)
        }
    }
}
