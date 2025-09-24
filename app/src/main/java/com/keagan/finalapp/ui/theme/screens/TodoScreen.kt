package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo
import com.keagan.finalapp.ui.theme.components.Segmented
import com.keagan.finalapp.ui.theme.components.TinyTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(repo: Repo, modifier: Modifier = Modifier) {
    val tasks = repo.tasks.collectAsState(initial = emptyList()).value
    var filter by remember { mutableStateOf(0) } // 0 All, 1 Active, 2 Done
    var showAdd by remember { mutableStateOf(false) }

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("My Tasks ✅", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { showAdd = true }) { Text("+ Add") }
        }
        Spacer(Modifier.height(10.dp))
        Segmented(listOf("All", "Active", "Done"), filter) { filter = it }
        Spacer(Modifier.height(12.dp))

        val list = when (filter) { 1 -> tasks.filter { !it.done }; 2 -> tasks.filter { it.done }; else -> tasks }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(list, key = { it.id }) { t ->
                Card {
                    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row {
                            Checkbox(checked = t.done, onCheckedChange = { repo.toggleTask(t.id) })
                            Column {
                                Text(t.title, style = MaterialTheme.typography.titleMedium)
                                if (t.tag != null) TinyTag(t.tag)
                            }
                        }
                        TextButton(onClick = { repo.deleteTask(t.id) }) { Text("Delete") }
                    }
                }
            }
        }
    }

    if (showAdd) {
        var title by remember { mutableStateOf("") }
        var tag by remember { mutableStateOf<String?>(null) }
        ModalBottomSheet(onDismissRequest = { showAdd = false }) {
            Column(Modifier.padding(16.dp)) {
                Text("Add Task", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                Text("Tag")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = tag=="study", onClick = { tag="study" }, label = { Text("study") })
                    FilterChip(selected = tag=="personal", onClick = { tag="personal" }, label = { Text("personal") })
                    FilterChip(selected = tag=="urgent", onClick = { tag="urgent" }, label = { Text("urgent") })
                }
                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { showAdd = false }) { Text("Cancel") }
                    Button(onClick = { if (title.isNotBlank()) { repo.addTask(title.trim(), tag); showAdd = false } }) { Text("Save") }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
