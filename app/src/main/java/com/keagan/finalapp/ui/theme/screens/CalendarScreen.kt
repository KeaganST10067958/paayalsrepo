package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.data.Repo

@Composable
fun CalendarScreen(repo: Repo) {
    val events by repo.events.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Calendar", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            items(events, key = { it.id }) { event ->
                Card(modifier = Modifier.padding(top = 8.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(event.title, style = MaterialTheme.typography.titleMedium)
                        Text("${event.start} - ${event.end}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
