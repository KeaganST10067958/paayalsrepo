package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo
import com.keagan.finalapp.ui.theme.Routes
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    repo: Repo,                             // <-- Repo here
    displayName: String = "",
    onNavSelect: (String) -> Unit = {},
    onProfile: () -> Unit = {}
) {
    val tasksSize = repo.tasks.map { it.size }.collectAsState(initial = 0).value
    val notesSize = repo.notes.map { it.size }.collectAsState(initial = 0).value
    val eventsSize = repo.events.map { it.size }.collectAsState(initial = 0).value

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                Text(
                    text = if (displayName.isNotBlank()) "Hi, $displayName" else "Dashboard",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            actions = { Button(onClick = onProfile) { Text("Profile") } }
        )

        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatCard("Tasks", tasksSize.toString())
                StatCard("Notes", notesSize.toString())
                StatCard("Events", eventsSize.toString())
            }

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            SectionHeader("Go to", "Calendar") { onNavSelect(Routes.Calendar) }
            Spacer(Modifier.height(12.dp))
            SectionHeader("Go to", "To-Do") { onNavSelect(Routes.Todo) }
            Spacer(Modifier.height(12.dp))
            SectionHeader("Go to", "Notes") { onNavSelect(Routes.Notes) }
        }
    }
}

@Composable private fun StatCard(label: String, value: String) {
    Card(colors = CardDefaults.cardColors()) {
        Column(Modifier.padding(16.dp)) {
            Text(value, style = MaterialTheme.typography.headlineSmall)
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable private fun SectionHeader(title: String, actionLabel: String, onAction: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Button(onClick = onAction) { Text(actionLabel) }
    }
}
