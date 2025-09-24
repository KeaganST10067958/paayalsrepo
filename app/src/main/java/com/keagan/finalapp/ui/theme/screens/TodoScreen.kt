package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo

@Composable
fun TodoScreen(repo: Repo, modifier: Modifier = Modifier) {
    var tab by remember { mutableStateOf(0) } // 0 All, 1 Active, 2 Done
    var newTask by remember { mutableStateOf("") }

    Scaffold(modifier = modifier) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            Text("My Tasks ✅", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            // Tabs like mockups
            SingleChoiceSegmentedButtonRow {
                listOf("All", "Active", "Done").forEachIndexed { i, label ->
                    SegmentedButton(
                        selected = i == tab,
                        onClick = { tab = i },
                        shape = SegmentedButtonDefaults.itemShape(i, 3),
                        label = { Text(label) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))

            // Add bar
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("Quick add…") },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.large
                )
                Button(
                    onClick = { if (newTask.isNotBlank()) { repo.addTask(newTask.trim()); newTask = "" } },
                    enabled = newTask.isNotBlank()
                ) { Text("+ Add") }
            }

            Spacer(Modifier.height(12.dp))

            val tasks = repo.tasks.collectAsState(initial = emptyList()).value
            val filtered = when (tab) {
                1 -> tasks.filter { !it.done }
                2 -> tasks.filter { it.done }
                else -> tasks
            }

            if (filtered.isEmpty()) {
                Spacer(Modifier.height(24.dp))
                Text("No tasks here yet.", color = MaterialTheme.colorScheme.onSurface.copy(.6f))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(filtered, key = { it.id }) { task ->
                        Card {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Checkbox(checked = task.done, onCheckedChange = { repo.toggleTask(task.id) })
                                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                                }
                                TextButton(onClick = { repo.deleteTask(task.id) }) { Text("Delete") }
                            }
                        }
                    }
                }
            }
        }
    }
}
