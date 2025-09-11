package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo

@Composable
fun TodoScreen(repo: Repo) {
    val tasks by repo.tasks.collectAsState(initial = emptyList())
    val text = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("To-Do", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("New task") }
            )
            Button(
                enabled = text.value.isNotBlank(),
                onClick = {
                    repo.addTask(text.value.trim())
                    text.value = ""
                }
            ) { Text("Add") }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks, key = { it.id }) { task ->
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { repo.toggleTask(task.id) }
                            )
                            Text(task.title, style = MaterialTheme.typography.bodyLarge)
                        }
                        Button(onClick = { repo.deleteTask(task.id) }) { Text("Delete") }
                    }
                }
            }
        }
    }
}
