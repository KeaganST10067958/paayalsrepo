package com.keagan.finalapp.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.model.NoteColor
import com.keagan.finalapp.ui.theme.model.swatch

private data class NoteUI(
    val id: Long,
    val text: String,
    val color: NoteColor
)

@Composable
fun NotesScreen(modifier: Modifier = Modifier) {
    // simple in-memory notes
    val notes = remember { mutableStateListOf<NoteUI>() }

    // add-panel state
    var showAdd by remember { mutableStateOf(false) }
    var draft by remember { mutableStateOf("") }
    var picked by remember { mutableStateOf(NoteColor.Peach) }

    Box(
        modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7FB))
            .padding(16.dp)
    ) {
        Column {
            // Header
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "My Notes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFA5C98)
                )
                Button(
                    onClick = { showAdd = true },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA5C98))
                ) {
                    Text("+ Add")
                }
            }

            Spacer(Modifier.height(14.dp))

            // Two-column “masonry” layout like the mockup
            if (notes.isEmpty()) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    tonalElevation = 1.dp,
                    border = BorderStroke(1.dp, Color(0x10000000))
                ) {
                    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "No notes yet",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Tap + Add to create your first sticky note.",
                            color = Color(0xFF8A91A1)
                        )
                    }
                }
            } else {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StickyColumn(
                        items = notes.filterIndexed { i, _ -> i % 2 == 0 },
                        owner = notes,
                        modifier = Modifier.weight(1f)
                    )
                    StickyColumn(
                        items = notes.filterIndexed { i, _ -> i % 2 == 1 },
                        owner = notes,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // --- Add Note panel (overlay) ---
        AnimatedVisibility(visible = showAdd) {
            Surface(
                tonalElevation = 3.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .align(Alignment.BottomCenter)
            ) {
                Column(Modifier.padding(16.dp)) {
                    // grab handle
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Box(
                            Modifier
                                .width(56.dp)
                                .height(5.dp)
                                .background(Color(0xFFE8EAF2), RoundedCornerShape(3.dp))
                        )
                    }
                    Spacer(Modifier.height(12.dp))

                    Text("Add a Note", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = draft,
                        onValueChange = { draft = it },
                        placeholder = { Text("Type your note…") },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(Modifier.height(12.dp))
                    Text("Colour", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        NoteColor.entries.forEach { c: NoteColor ->
                            val selected = c == picked
                            Surface(
                                onClick = { picked = c },
                                shape = RoundedCornerShape(12.dp),
                                color = c.swatch,
                                border = if (selected) BorderStroke(2.dp, Color(0xFFFA5C98)) else null
                            ) { Box(Modifier.size(48.dp)) }
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    Text("Preview", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = picked.swatch,
                        border = BorderStroke(1.dp, Color(0x14000000))
                    ) {
                        Text(
                            draft.ifBlank { "Type your note…" },
                            modifier = Modifier.padding(14.dp),
                            maxLines = 6,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        TextButton(onClick = { showAdd = false }) { Text("Cancel") }
                        Button(
                            onClick = {
                                if (draft.isNotBlank()) {
                                    notes.add(
                                        0,
                                        NoteUI(
                                            id = System.currentTimeMillis(),
                                            text = draft.trim(),
                                            color = picked
                                        )
                                    )
                                    draft = ""
                                    showAdd = false
                                }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA5C98))
                        ) { Text("Save note") }
                    }
                }
            }
        }
    }
}

@Composable
private fun StickyColumn(
    items: List<NoteUI>,
    owner: MutableList<NoteUI>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items, key = { it.id }) { note ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = note.color.swatch,
                tonalElevation = 0.dp,
                border = BorderStroke(1.dp, Color(0x1A000000))
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(note.text, maxLines = 8, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(
                            "×",
                            modifier = Modifier
                                .clickable { owner.removeAll { it.id == note.id } }
                                .border(BorderStroke(1.dp, Color(0x33000000)), RoundedCornerShape(10.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
