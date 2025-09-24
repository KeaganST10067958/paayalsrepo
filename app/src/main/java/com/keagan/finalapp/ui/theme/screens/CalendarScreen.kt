package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.EventItem
import com.keagan.finalapp.data.Repo
import java.time.*
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(repo: Repo, modifier: Modifier = Modifier) {
    val events = repo.events.collectAsState(initial = emptyList()).value
    var month by remember { mutableStateOf(YearMonth.now()) }
    var showAdd by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }

    val monthDays = monthAtAGlance(month)
    val dayEvents = events.filter { Instant.ofEpochMilli(it.start).atZone(ZoneId.systemDefault()).toLocalDate() == selectedDay }

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("<", modifier = Modifier.clickable { month = month.minusMonths(1) })
            Text(month.format(DateTimeFormatter.ofPattern("MMMM yyyy")), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Text(">", modifier = Modifier.clickable { month = month.plusMonths(1) })
        }
        Spacer(Modifier.height(8.dp))

        // Week header
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat").forEach { Text(it, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Gray) }
        }
        Spacer(Modifier.height(4.dp))

        // Calendar grid
        monthDays.chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                week.forEach { date ->
                    val isThisMonth = date.month == month.month
                    val isSelected = date == selectedDay
                    val dots = eventsForDate(events, date).size
                    Column(
                        modifier = Modifier.weight(1f)
                            .clip(MaterialTheme.shapes.small)
                            .border(1.dp, Color(0x11000000), MaterialTheme.shapes.small)
                            .background(if (isSelected) Color(0x1FFF4D88) else Color.Transparent)
                            .padding(vertical = 10.dp)
                            .clickable { selectedDay = date },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(date.dayOfMonth.toString(), color = if (isThisMonth) Color.Unspecified else Color.LightGray)
                        if (dots > 0) Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            repeat(minOf(3, dots)) { Box(Modifier.size(6.dp).clip(MaterialTheme.shapes.extraSmall).background(Color(0xFF7ED957))) }
                        }
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
        }

        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(onClick = { selectedDay = LocalDate.now(); month = YearMonth.now() }, label = { Text("Today") })
            AssistChip(onClick = { /* filter all */ }, label = { Text("All") })
            Button(onClick = { showAdd = true }) { Text("+ Event") }
        }
        Spacer(Modifier.height(12.dp))

        Text("Agenda • ${selectedDay.format(DateTimeFormatter.ofPattern("EEE, d MMM"))}", style = MaterialTheme.typography.titleMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(dayEvents, key = { it.id }) { e -> AgendaCard(e) }
        }
    }

    if (showAdd) AddEventSheet(selectedDay, onDismiss = { showAdd = false }) { title, start, end, type ->
        repo.addEvent(title, start, end, type); showAdd = false
    }
}

private fun eventsForDate(list: List<EventItem>, date: LocalDate): List<EventItem> {
    val z = ZoneId.systemDefault()
    return list.filter {
        val d = Instant.ofEpochMilli(it.start).atZone(z).toLocalDate()
        d == date
    }
}

private fun monthAtAGlance(month: YearMonth): List<LocalDate> {
    val first = month.atDay(1)
    val last = month.atEndOfMonth()
    val start = first.minusDays((first.dayOfWeek.value % 7).toLong())
    val end = last.plusDays(6 - (last.dayOfWeek.value % 7).toLong())
    return generateSequence(start) { it.plusDays(1) }.takeWhile { !it.isAfter(end) }.toList()
}

@Composable private fun AgendaCard(e: EventItem) {
    val z = ZoneId.systemDefault()
    val s = Instant.ofEpochMilli(e.start).atZone(z).toLocalTime()
    val t = Instant.ofEpochMilli(e.end).atZone(z).toLocalTime()
    Card { Column(Modifier.padding(12.dp)) {
        Text(e.title, style = MaterialTheme.typography.titleMedium)
        Text("${s} – ${t}", style = MaterialTheme.typography.bodySmall)
    } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEventSheet(day: LocalDate, onDismiss: () -> Unit, onSave: (String, Long, Long, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var start by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var end by remember { mutableStateOf(LocalTime.of(10, 0)) }
    var type by remember { mutableStateOf("study") }

    val z = ZoneId.systemDefault()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Add Event", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = start.toString(), onValueChange = { runCatching { start = LocalTime.parse(it) } }, label = { Text("Start (HH:MM)") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = end.toString(), onValueChange = { runCatching { end = LocalTime.parse(it) } }, label = { Text("End (HH:MM)") }, modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
            Text("Type / Colour")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = type=="study", onClick = { type="study" }, label = { Text("Study") })
                FilterChip(selected = type=="personal", onClick = { type="personal" }, label = { Text("Personal") })
                FilterChip(selected = type=="exam", onClick = { type="exam" }, label = { Text("Exam") })
                FilterChip(selected = type=="other", onClick = { type="other" }, label = { Text("Other") })
            }
            Spacer(Modifier.height(12.dp))
            val sEpoch = day.atTime(start).atZone(z).toInstant().toEpochMilli()
            val eEpoch = day.atTime(end).atZone(z).toInstant().toEpochMilli()
            Text("Preview • ${start}–${end}  $title")
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Button(onClick = { if (title.isNotBlank()) onSave(title.trim(), sEpoch, eEpoch, type) }) { Text("Save") }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}
