package com.keagan.finalapp.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import kotlinx.coroutines.delay
import com.keagan.finalapp.ui.theme.model.EventItem
import com.keagan.finalapp.ui.theme.model.NoteColor
import com.keagan.finalapp.ui.theme.model.StickyNote
import androidx.compose.foundation.BorderStroke

// ---------- simple local models for the dash ----------
private data class TodoItem(
    val id: Long,
    val title: String,
    val tag: String = "study",
    var done: Boolean = false
)

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    displayName: String = "Keagan",
    onOpenTodo: () -> Unit = {},
    onOpenCalendar: () -> Unit = {},
    onOpenNotes: () -> Unit = {}
) {
    val todos = remember { mutableStateListOf<TodoItem>() }
    val agenda = remember {
        mutableStateListOf(
            EventItem(1L, "09:00", "CS Lecture"),
            EventItem(2L, "12:30", "Study: Past Paper"),
            EventItem(3L, "15:00", "Group sync"),
            EventItem(4L, "18:00", "Gym"),
            EventItem(5L, "20:00", "Revise Chem")
        )
    }
    val notes = remember { mutableStateListOf<StickyNote>() }

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7FB))
            .padding(horizontal = 16.dp)
    ) {
        Header(displayName)
        StatsRow()

        Segmented(
            items = listOf("To-Do", "Schedule", "Pomodoro", "Notes"),
            selectedIndex = selectedTab,
            onSelected = { selectedTab = it }
        )
        Spacer(Modifier.height(8.dp))

        when (selectedTab) {
            0 -> TodoPanel(todos, onOpenTodo)
            1 -> SchedulePanel(agenda, onOpenCalendar)
            2 -> PomodoroPanel()
            3 -> NotesPanel(notes, onOpenNotes)
        }
        Spacer(Modifier.height(12.dp))
    }
}

// ---------- header / stats ----------
@Composable
private fun Header(name: String) {
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text("Hi, $name 👋", style = MaterialTheme.typography.labelLarge, color = Color(0xFF6C7380))
            Text(
                "Plan-demic",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = Color(0xFFFA5C98)
            )
        }
        Surface(
            modifier = Modifier.size(36.dp),
            color = Color(0xFFEFF3F9),
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 2.dp
        ) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("KS") } }
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun StatsRow() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        StatCard(Modifier.weight(1f), "Today", "0", "tasks", showBar = true)
        StatCard(Modifier.weight(1f), "Focus", "0", "min")
        StatCard(Modifier.weight(1f), "Streak", "3", "days")
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun StatCard(
    modifier: Modifier,
    title: String,
    value: String,
    sub: String,
    showBar: Boolean = false
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        color = Color.White
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = Color(0xFF8A91A1))
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.width(6.dp))
                Text(sub, style = MaterialTheme.typography.labelMedium, color = Color(0xFF8A91A1))
            }
            if (showBar) {
                Spacer(Modifier.height(8.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Color(0xFFF1F2F8), RoundedCornerShape(3.dp))
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.25f)
                            .background(Color(0xFFFA5C98), RoundedCornerShape(3.dp))
                    )
                }
            }
        }
    }
}

// ---------- segmented ----------
@Composable
private fun Segmented(items: List<String>, selectedIndex: Int, onSelected: (Int) -> Unit) {
    Surface(shape = RoundedCornerShape(22.dp), color = Color.White, tonalElevation = 1.dp, shadowElevation = 1.dp) {
        Row(
            Modifier.padding(6.dp).height(40.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { i, label ->
                val active = i == selectedIndex
                Surface(
                    onClick = { onSelected(i) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (active) Color(0xFFFAE5EF) else Color.Transparent
                ) {
                    Text(
                        label,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        color = if (active) Color(0xFFFA5C98) else Color(0xFF6C7380),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

// ---------- To-Do ----------
@Composable
private fun TodoPanel(todos: MutableList<TodoItem>, onOpenTodo: () -> Unit) {
    CardBlock(title = "To-Do") {
        var quick by remember { mutableStateOf(false) }
        var title by remember { mutableStateOf("") }
        var tag by remember { mutableStateOf("study") }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("+ Quick add", color = Color(0xFFFA5C98), modifier = Modifier.clickable { quick = !quick })
        }

        AnimatedVisibility(quick) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = title, onValueChange = { title = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Task title") }, singleLine = true, shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    FilterChip(selected = tag == "study", onClick = { tag = "study" }, label = { Text("Study") })
                    Spacer(Modifier.width(6.dp))
                    FilterChip(selected = tag == "personal", onClick = { tag = "personal" }, label = { Text("Personal") })
                    Spacer(Modifier.width(6.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                todos.add(0, TodoItem(System.currentTimeMillis(), title, tag))
                                title = ""; quick = false
                            }
                        },
                        shape = RoundedCornerShape(14.dp)
                    ) { Text("Add") }
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        if (todos.isEmpty()) {
            OutlinedButton(onClick = onOpenTodo, shape = RoundedCornerShape(24.dp)) { Text("Open To-Do") }
        } else {
            Spacer(Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 280.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(todos, key = { it.id }) { t ->
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        tonalElevation = 1.dp,
                        color = Color.White
                    ) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = t.done, onCheckedChange = { t.done = it })
                            Column(Modifier.weight(1f)) {
                                Text(t.title, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text("30 mins • Flashcards", style = MaterialTheme.typography.labelMedium, color = Color(0xFF8A91A1))
                            }
                            AssistChip(onClick = {}, label = { Text(t.tag.replaceFirstChar { it.uppercase() }) })
                        }
                    }
                }
            }
        }
    }
}

// ---------- Schedule ----------
@Composable
private fun SchedulePanel(agenda: List<EventItem>, onOpenCalendar: () -> Unit) {
    CardBlock(title = "Today") {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(agenda, key = { it.id }) { item ->
                Row(
                    Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(14.dp)).padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(color = Color(0xFFEFF3F9), shape = RoundedCornerShape(10.dp)) {
                        Text(item.time, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(item.title, fontWeight = FontWeight.SemiBold)
                }
            }
            item { Spacer(Modifier.height(4.dp)) }
            item { TextButton(onClick = onOpenCalendar) { Text("Open Calendar") } }
        }
    }
}

// ---------- Pomodoro ----------
@Composable
private fun PomodoroPanel() {
    var total by remember { mutableIntStateOf(25 * 60) }
    var remaining by remember { mutableIntStateOf(total) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running, total) {
        if (running) {
            while (remaining > 0 && running) {
                delay(1000)
                remaining--
            }
            running = false
        }
    }

    CardBlock {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SegButton("Pomodoro", selected = total == 25 * 60) { total = 25 * 60; remaining = total }
            SegButton("Short", selected = total == 5 * 60) { total = 5 * 60; remaining = total }
            SegButton("Long", selected = total == 15 * 60) { total = 15 * 60; remaining = total }
        }

        Spacer(Modifier.height(14.dp))

        val raw = if (total > 0) 1f - (remaining.toFloat() / total.toFloat()) else 0f
        val progress by animateFloatAsState(targetValue = raw.coerceIn(0f, 1f), label = "ring")

        CircularProgressIndicator(
            progress = { progress },
            strokeWidth = 10.dp,
            modifier = Modifier.size(220.dp).align(Alignment.CenterHorizontally),
            color = Color(0xFFDEE3F3),
            trackColor = Color(0xFFF5F6FA)
        )

        val mins = remaining / 60
        val secs = remaining % 60
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(String.format("%02d:%02d", mins, secs), style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { running = !running },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA5C98))
            ) { Text(if (running) "Pause" else "Start") }
            Spacer(Modifier.width(12.dp))
            TextButton(onClick = { running = false; remaining = total }) { Text("Reset") }
        }
    }
}

@Composable private fun SegButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (selected) Color(0xFFEFF3F9) else Color.White,
        tonalElevation = 1.dp
    ) { Text(text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) }
}

// ---------- Notes ----------
@Composable
private fun NotesPanel(notes: MutableList<StickyNote>, onOpenNotes: () -> Unit) {
    CardBlock(title = "Sticky Notes") {
        var picker by remember { mutableStateOf(NoteColor.Mint) }
        var showAdd by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            NoteColor.values().forEach { c ->
                Box(
                    Modifier
                        .size(36.dp)
                        .background(c.swatch, RoundedCornerShape(10.dp))
                        .border(
                            if (c == picker) BorderStroke(2.dp, Color(0xFFFA5C98)) else BorderStroke(0.dp, Color.Transparent),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { picker = c }
                )
                Spacer(Modifier.width(8.dp))
            }
            Button(onClick = { showAdd = true }, shape = RoundedCornerShape(14.dp)) { Text("Add") }
        }

        AnimatedVisibility(showAdd) {
            Column {
                OutlinedTextField(
                    value = text, onValueChange = { text = it },
                    placeholder = { Text("Type your note…") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
                Spacer(Modifier.height(8.dp))
                Row {
                    Button(
                        onClick = {
                            if (text.isNotBlank()) {
                                notes.add(0, StickyNote(System.currentTimeMillis(), text, picker))
                                text = ""; showAdd = false
                            }
                        },
                        shape = RoundedCornerShape(14.dp)
                    ) { Text("Save") }
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { showAdd = false }) { Text("Cancel") }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        if (notes.isEmpty()) {
            OutlinedButton(onClick = onOpenNotes, shape = RoundedCornerShape(24.dp)) { Text("Open Notes") }
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
}

@Composable
private fun StickyColumn(
    items: List<StickyNote>,
    owner: MutableList<StickyNote>,
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
                tonalElevation = 0.dp
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(note.text, maxLines = 8, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { owner.removeAll { it.id == note.id } }) { Text("×") }
                    }
                }
            }
        }
    }
}

// ---------- card shell ----------
@Composable
private fun CardBlock(title: String? = null, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            if (title != null) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
            }
            content()
        }
    }
}
