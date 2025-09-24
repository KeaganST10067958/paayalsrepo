package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(repo: Repo, modifier: Modifier = Modifier) {
    var tab by remember { mutableStateOf(0) } // 0 All, 1 Pinned
    var showAdd by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(0xFFFFF59DL) } // light yellow

    Scaffold(modifier = modifier) { pad ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Notes", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = { showAdd = true }) { Text("+ Note") }
            }
            Spacer(Modifier.height(12.dp))

            SingleChoiceSegmentedButtonRow {
                listOf("All", "Pinned").forEachIndexed { i, label ->
                    SegmentedButton(
                        selected = tab == i,
                        onClick = { tab = i },
                        shape = SegmentedButtonDefaults.itemShape(i, 2),
                        label = { Text(label) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))

            val notes = repo.notes.collectAsState(initial = emptyList()).value
            val filtered = if (tab == 1) notes.filter { it.pinned } else notes

            if (filtered.isEmpty()) {
                Spacer(Modifier.height(24.dp))
                Text("No notes yet.", color = MaterialTheme.colorScheme.onSurface.copy(.6f))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filtered, key = { it.id }) { n ->
                        val bg = colorFromLong(n.color)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = bg),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(n.title, style = MaterialTheme.typography.titleMedium, color = readableOn(bg))
                                Spacer(Modifier.height(6.dp))
                                Text(n.text, style = MaterialTheme.typography.bodyMedium, color = readableOn(bg))
                                Spacer(Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TextButton(onClick = { repo.deleteNote(n.id) }) { Text("Delete") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        AlertDialog(
            onDismissRequest = { showAdd = false },
            title = { Text("Add Note") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                    OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Text") })
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(
                            0xFFFFF59DL, // Peach-ish
                            0xFFC8E6C9L, // Mint
                            0xFFBBDEFB, // Blue
                            0xFFE1BEE7, // Lavender
                            0xFFFFF9C4 // Lemon
                        ).forEach { c ->
                            AssistChip(
                                onClick = { color = c.toLong() },
                                label = { Text(" ") },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = colorFromLong(c.toLong())
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (title.isNotBlank() || text.isNotBlank()) {
                        repo.addNote(title.trim().ifBlank { "Untitled" }, text.trim(), color)
                    }
                    title = ""; text = ""; color = 0xFFFFF59DL; showAdd = false
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel") } }
        )
    }
}

private fun colorFromLong(argb: Long): Color {
    val a = ((argb shr 24) and 0xFF).toInt()
    val r = ((argb shr 16) and 0xFF).toInt()
    val g = ((argb shr 8) and 0xFF).toInt()
    val b = (argb and 0xFF).toInt()
    return Color(r, g, b, a)
}
private fun readableOn(bg: Color): Color {
    val y = 0.2126f * bg.red + 0.7152f * bg.green + 0.0722f * bg.blue
    return if (y > 0.5f) Color.Black else Color.White
}
