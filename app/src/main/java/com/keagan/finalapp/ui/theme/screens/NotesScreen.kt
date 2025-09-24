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
import com.keagan.finalapp.data.StickyNote
import com.keagan.finalapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(repo: Repo, modifier: Modifier = Modifier) {
    val all = repo.notes.collectAsState(initial = emptyList()).value
    var tab by remember { mutableStateOf(0) } // 0 All, 1 Pinned
    var query by remember { mutableStateOf("") }
    var showAdd by remember { mutableStateOf(false) }

    val notes = all.filter { (tab==0 || it.pinned) && (query.isBlank() || it.title.contains(query, true) || it.text.contains(query, true)) }

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Notes", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { showAdd = true }) { Text("+ Note") }
        }
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = query, onValueChange = { query = it }, placeholder = { Text("Search notes...") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        SingleChoiceSegmentedButtonRow {
            SegmentedButton(selected = tab==0, onClick = { tab=0 }, shape = SegmentedButtonDefaults.itemShape(0,2), label = { Text("All") })
            SegmentedButton(selected = tab==1, onClick = { tab=1 }, shape = SegmentedButtonDefaults.itemShape(1,2), label = { Text("Pinned") })
        }
        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 160.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
            items(notes, key = { it.id }) { n -> NoteCard(n, onPin = { repo.togglePin(n.id) }, onDelete = { repo.deleteNote(n.id) }) }
        }
    }

    if (showAdd) AddNoteSheet(onDismiss = { showAdd = false }) { title, text, color ->
        repo.addNote(title, text, color); showAdd = false
    }
}

@Composable
private fun NoteCard(n: StickyNote, onPin: () -> Unit, onDelete: () -> Unit) {
    val bg = colorFromLong(n.color)
    Card(colors = CardDefaults.cardColors(containerColor = bg)) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(n.title, style = MaterialTheme.typography.titleMedium, color = readableOn(bg))
                Row {
                    TextButton(onClick = onPin) { Text(if (n.pinned) "Unpin" else "Pin") }
                    TextButton(onClick = onDelete) { Text("Del") }
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(n.text, color = readableOn(bg))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNoteSheet(onDismiss: () -> Unit, onSave: (String, String, Long) -> Unit) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(0xFFFFE0E0L) } // Peach default

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Add Note", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Text") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Text("Colour")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ColorChip("Peach", Peach) { color = 0xFFFFE0E0L }
                ColorChip("Mint", Mint) { color = 0xFFDFF3E6L }
                ColorChip("Blue", Blue) { color = 0xFFE0ECFFL } // corrected below
                ColorChip("Lav", Lavender) { color = 0xFFE7E0FFL }
                ColorChip("Lemon", Lemon) { color = 0xFFFFF5CCL }
            }
            Spacer(Modifier.height(12.dp))
            Card(colors = CardDefaults.cardColors(containerColor = colorFromLong(color))) {
                Column(Modifier.padding(12.dp)) {
                    Text(if (title.isBlank()) "Untitled" else title)
                    Text(if (text.isBlank()) "Type your note..." else text)
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Button(onClick = { if (title.isNotBlank()) onSave(title.trim(), text.trim(), color) }) { Text("Save") }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ColorChip(label: String, swatch: Color, onPick: () -> Unit) {
    FilterChip(
        selected = false,
        onClick = onPick,
        label = { Text(label) },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(swatch, CircleShape)
            )
        }
    )
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
