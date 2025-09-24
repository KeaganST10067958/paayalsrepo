package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(repo: Repo, modifier: Modifier = Modifier) {
    var month by remember { mutableStateOf(YearMonth.now()) }
    var selected by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Month header
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { month = month.minusMonths(1) }) { Text("‹") }
            Text(
                month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " ${month.year}",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { month = month.plusMonths(1) }) { Text("›") }
        }
        Spacer(Modifier.height(12.dp))

        // Weekday labels
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat").forEach {
                Text(it, style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
        Spacer(Modifier.height(8.dp))

        // Calendar grid (simple)
        val firstDay = month.atDay(1)
        val leading = (firstDay.dayOfWeek.value % 7) // Sunday = 0
        val days = month.lengthOfMonth()
        val cells = leading + days
        Column {
            for (row in 0 until ((cells + 6) / 7)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    for (col in 0..6) {
                        val index = row * 7 + col
                        val dayNum = index - leading + 1
                        val isInMonth = dayNum in 1..days
                        val date = if (isInMonth) month.atDay(dayNum) else null
                        val selectedStyle = if (date == selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .aspectRatio(1f)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .background(selectedStyle, shape = MaterialTheme.shapes.medium)
                                .clickable(enabled = isInMonth) { selected = date!! },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                if (isInMonth) "$dayNum" else "",
                                color = if (date == selected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Agenda", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        val events = repo.events.collectAsState(initial = emptyList()).value
        val todays = events.filter { LocalDate.ofEpochDay(it.start / 86_400_000L) == selected }

        if (todays.isEmpty()) {
            Text("No events for this day.", color = MaterialTheme.colorScheme.onSurface.copy(.6f))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(todays.size) { i ->
                    val e = todays[i]
                    Card { Column(Modifier.padding(12.dp)) { Text(e.title); Text("${e.start} – ${e.end}") } }
                }
            }
        }
    }
}
