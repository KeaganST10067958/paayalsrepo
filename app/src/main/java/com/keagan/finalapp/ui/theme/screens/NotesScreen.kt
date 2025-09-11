package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo

@Composable
fun NotesScreen(repo: Repo) {                    // <-- Repo here
    val notes = repo.notes.collectAsState(initial = emptyList()).value

    Column(Modifier.padding(16.dp)) {
        Text("Notes", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(12.dp))

        notes.forEach { note ->
            val bg = colorFromLong(note.color)
            Card(colors = CardDefaults.cardColors(containerColor = bg),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text(note.title, style = MaterialTheme.typography.titleMedium, color = readableOn(bg))
                    Spacer(Modifier.height(6.dp))
                    Text(note.text, style = MaterialTheme.typography.bodyMedium, color = readableOn(bg))
                }
            }
        }
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
