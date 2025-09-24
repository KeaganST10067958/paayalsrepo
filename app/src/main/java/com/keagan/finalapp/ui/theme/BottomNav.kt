package com.keagan.finalapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    // Container with rounded top corners and soft elevation (neumorphic-ish)
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.9f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        tonalElevation = 6.dp,
        shadowElevation = 8.dp
    ) {
        // Safe-area style padding + generous vertical space
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Home,
                label = "Dashboard",
                selected = currentRoute == Routes.Dashboard,
                onClick = { onNavigate(Routes.Dashboard) }
            )
            NavButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.CheckCircle,
                label = "To-Do",
                selected = currentRoute == Routes.Todo,
                onClick = { onNavigate(Routes.Todo) }
            )
            NavButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.CalendarMonth,
                label = "Calendar",
                selected = currentRoute == Routes.Calendar,
                onClick = { onNavigate(Routes.Calendar) }
            )
            NavButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.StickyNote2,
                label = "Notes",
                selected = currentRoute == Routes.Notes,
                onClick = { onNavigate(Routes.Notes) }
            )
            NavButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Filled.Face,
                label = "Me",
                selected = currentRoute == Routes.Profile,
                onClick = { onNavigate(Routes.Profile) }
            )
        }
    }
}

@Composable
private fun NavButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bubbleBg = if (selected) BrandPink.copy(alpha = 0.18f) else Color(0xFFF2F2F5)
    val iconTint = if (selected) BrandPink else Color(0xFF5F5F66)
    val textColor = if (selected) BrandPink else Color(0xFF5F5F66)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon bubble
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(bubbleBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
