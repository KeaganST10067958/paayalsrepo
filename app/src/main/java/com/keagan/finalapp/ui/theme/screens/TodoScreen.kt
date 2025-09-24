package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.foundation.TaskTag
import com.keagan.finalapp.ui.theme.foundation.swatch
import androidx.compose.runtime.Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(modifier: Modifier = Modifier) {
    var tab by remember { mutableStateOf(TodoTab.All) }

    // Mock items just for UI layout
    val all = remember {
        listOf(
            TodoUi("Revise Chemistry", "Flashcards 20min", TaskTag.Study, false),
            TodoUi("Buy snacks", "For study group", TaskTag.Personal, true),
            TodoUi("Essay draft", "Due Friday", TaskTag.Urgent, false),
        )
    }
    val active = all.filter { !it.done }
    val done = all.filter { it.done }
    val list = when (tab) {
        TodoTab.All -> all
        TodoTab.Active -> active
        TodoTab.Done -> done
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("My Tasks",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFA5C98)
                            )
                        )
                        Spacer(Modifier.width(6.dp))
                        Box(
                            Modifier
                                .size(18.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE8F7EF)),
                            contentAlignment = Alignment.Center
                        ) { Text("✓", style = MaterialTheme.typography.labelSmall) }
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { /* open bottom sheet later */ },
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFFFF8FB3))
                    ) { Text("+ Add", color = Color.White) }
                }
            )
        }
    ) { pad ->
        Column(
            modifier
                .fillMaxSize()
                .padding(pad)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // Segmented control
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SegChip("All", tab == TodoTab.All) { tab = TodoTab.All }
                SegChip("Active", tab == TodoTab.Active) { tab = TodoTab.Active }
                SegChip("Done", tab == TodoTab.Done) { tab = TodoTab.Done }
            }

            Spacer(Modifier.height(16.dp))

            // Cards list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 28.dp)
            ) {
                items(list) { item ->
                    TaskCard(item)
                }
            }
        }
    }
}

private enum class TodoTab { All, Active, Done }

private data class TodoUi(
    val title: String,
    val subtitle: String,
    val tag: TaskTag,
    val done: Boolean
)

@Composable
private fun SegChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) Color(0xFFFFE3EC) else Color(0xFFF4F5F9)
    val tint = if (selected) Color(0xFFFA5C98) else Color(0xFF7E8795)
    Surface(
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(12.dp),
        color = bg,
        onClick = onClick
    ) {
        Box(Modifier.padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
            Text(text, color = tint, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun TaskCard(item: TodoUi) {
    val tagSwatch = swatch(item.tag)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 1.dp // subtle lift
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Checkbox mimic
                Box(
                    Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, Color(0xFFE2E5EC), RoundedCornerShape(6.dp))
                        .background(if (item.done) Color(0xFFE8F7EF) else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    if (item.done) Text("✓", style = MaterialTheme.typography.labelSmall)
                }

                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        item.title,
                        style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF2D3142))
                    )
                    Text(
                        item.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF8A90A3))
                    )
                }

                // Right aligned badge — use Spacer(weight) not Modifier.align
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(tagSwatch.bg)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(tagSwatch.label, color = tagSwatch.tint, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
