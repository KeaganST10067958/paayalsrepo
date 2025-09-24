package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.model.EventItem

@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    // Demo data (replace with repo later if you want)
    val events = remember {
        mutableStateListOf(
            EventItem(id = 1L, time = "09:00", title = "CS Lecture"),
            EventItem(id = 2L, time = "12:30", title = "Study: Past Paper"),
            EventItem(id = 3L, time = "15:00", title = "Group sync"),
            EventItem(id = 4L, time = "18:00", title = "Gym"),
            EventItem(id = 5L, time = "20:00", title = "Revise Chem"),
        )
    }

    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7FB))
            .padding(16.dp)
    ) {
        Text(
            "Schedule",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
            color = Color(0xFFFA5C98)
        )
        Spacer(Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            tonalElevation = 1.dp,
            shadowElevation = 1.dp
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Today", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(events, key = { it.id }) { item ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(14.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(color = Color(0xFFEFF3F9), shape = RoundedCornerShape(10.dp)) {
                                Text(
                                    item.time,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Text(item.title, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
