package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(24.dp)) {
        Text("Keagan Shaw", style = MaterialTheme.typography.headlineSmall)
        Text("keagan@example.com")
        Spacer(Modifier.height(16.dp)); Divider()
        ListItem(headlineContent = { Text("Account") })
        ListItem(headlineContent = { Text("Notifications") })
        ListItem(headlineContent = { Text("Theme") })
        ListItem(headlineContent = { Text("Privacy") })
        ListItem(headlineContent = { Text("Help & Support") })
        Spacer(Modifier.height(12.dp))
        TextButton(onClick = onBack) { Text("Logout") }
    }
}
