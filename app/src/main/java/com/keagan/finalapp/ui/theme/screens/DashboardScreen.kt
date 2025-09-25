package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo
import com.keagan.finalapp.ui.theme.components.Segmented
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    repo: Repo,
    displayName: String = "",
    onNavSelect: (String) -> Unit = {},
    onProfile: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val tasksSize = repo.tasks.map { it.size }.collectAsState(0).value
    val notesSize = repo.notes.map { it.size }.collectAsState(0).value
    val eventsSize = repo.events.map { it.size }.collectAsState(0).value

    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("To-Do", "Schedule", "Pomodoro", "Notes")

    Column(modifier.fillMaxSize().padding(16.dp)) {
        TopAppBar(title = { Text(if (displayName.isNotBlank()) "Hi, $displayName" else "Dashboard") },
            actions = { TextButton(onClick = onProfile) { Text("Profile") } })
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Today", tasksSize.toString())
            StatCard("Focus", "0")
            StatCard("Streak", "3 days")
        }

        Spacer(Modifier.height(12.dp))
        Segmented(
            options = tabs,
            selectedIndex = tab,
            onSelected = { tab = it }
        )
        Spacer(Modifier.height(12.dp))

        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            when (tab) {
                0 -> Text("Quick add + To-Do list (see To-Do tab)")
                1 -> Text("Today schedule list (see Calendar tab)")
                2 -> PomodoroTimer()
                3 -> Text("Sticky Notes (see Notes tab)")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable private fun StatCard(label: String, value: String) {
    Card { Column(Modifier.padding(14.dp)) {
        Text(value, style = MaterialTheme.typography.headlineSmall)
        Text(label, style = MaterialTheme.typography.labelLarge)
    } }
}

@Composable private fun PomodoroTimer() {
    var seconds by remember { mutableStateOf(25 * 60) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running, seconds) {
        while (running && seconds > 0) {
            delay(1000); seconds--
        }
        if (seconds == 0) running = false
    }

    Column {
        Text(String.format("%02d:%02d", seconds / 60, seconds % 60), style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { running = true }) { Text("Start") }
            TextButton(onClick = { running = false }) { Text("Reset") }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(onClick = { seconds = 5 * 60 }, label = { Text("Short") })
            AssistChip(onClick = { seconds = 15 * 60 }, label = { Text("Long") })
        }
    }
}
