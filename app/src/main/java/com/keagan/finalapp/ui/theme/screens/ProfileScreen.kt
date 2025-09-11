package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Profile")
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = onBack) { Text("Back") }
    }
}
