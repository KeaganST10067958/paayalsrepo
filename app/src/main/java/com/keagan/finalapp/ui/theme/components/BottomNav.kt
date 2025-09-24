package com.keagan.finalapp.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.FaceRetouchingNatural
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.StickyNote2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

object Routes {
    const val Dashboard = "dashboard"
    const val Todo = "todo"
    const val Calendar = "calendar"
    const val Notes = "notes"
    const val Profile = "profile"
    const val Splash = "splash"
    const val AfterSplash = "after_splash"
    const val Login = "login"
    const val SignUp = "signup"
}

private data class Tab(val route: String, val label: String, val icon: ImageVector)

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val tabs = listOf(
        Tab(Routes.Dashboard, "Dashboard", Icons.Rounded.Home),
        Tab(Routes.Todo,       "To-Do",     Icons.Rounded.CheckCircle),
        Tab(Routes.Calendar,   "Calendar",  Icons.Rounded.CalendarMonth),
        Tab(Routes.Notes,      "Notes",     Icons.Rounded.StickyNote2),
        Tab(Routes.Profile,    "Me",        Icons.Rounded.FaceRetouchingNatural),
    )

    NavigationBar(
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    ) {
        tabs.forEach { tab ->
            val selected = currentRoute == tab.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(tab.route) },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                alwaysShowLabel = true
            )
        }
    }
}
