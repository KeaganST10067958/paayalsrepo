package com.keagan.finalapp.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

data class NavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val items = listOf(
    NavItem(Routes.Dashboard, "Dashboard", Icons.Outlined.Home),
    NavItem(Routes.Todo, "To-Do", Icons.Outlined.CheckCircle),
    NavItem(Routes.Calendar, "Calendar", Icons.Outlined.CalendarMonth),
    NavItem(Routes.Notes, "Notes", Icons.Outlined.StickyNote2),
    NavItem(Routes.Profile, "Me", Icons.Outlined.EmojiEmotions)
)

@Composable
fun AppBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        items.forEach { i ->
            NavigationBarItem(
                selected = i.route == currentRoute,
                onClick = { onNavigate(i.route) },
                icon = { Icon(i.icon, i.label) },
                label = { Text(i.label) }
            )
        }
    }
}
