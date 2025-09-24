package com.keagan.finalapp.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.keagan.finalapp.ui.theme.components.AppBottomBar
import com.keagan.finalapp.ui.theme.components.Routes
import com.keagan.finalapp.ui.theme.screens.*

@Composable
fun AppNavGraph(
    startDestination: String = Routes.Splash
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.Splash) {
            SplashScreen(
                onDone = {
                    navController.navigate(Routes.AfterSplash) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AfterSplash) {
            AfterSplashScreen(
                onLogin = { navController.navigate(Routes.Login) },
                onSignUp = { navController.navigate(Routes.SignUp) }
            )
        }

        composable(Routes.Login) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.AfterSplash) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SignUp) {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.AfterSplash) { inclusive = true }
                    }
                }
            )
        }

        // ----- MAIN TABS -----
        composable(Routes.Dashboard) {
            val backStack by navController.currentBackStackEntryAsState()
            Scaffold(
                bottomBar = {
                    AppBottomBar(
                        currentRoute = backStack?.destination?.route,
                        onNavigate = { route ->
                            navController.navigate(route) { launchSingleTop = true }
                        }
                    )
                }
            ) { pad ->
                DashboardScreen(
                    modifier = Modifier.padding(pad),
                    displayName = "Keagan",
                    onOpenTodo = { navController.navigate(Routes.Todo) },
                    onOpenCalendar = { navController.navigate(Routes.Calendar) },
                    onOpenNotes = { navController.navigate(Routes.Notes) }
                )
            }
        }

        composable(Routes.Todo) {
            Scaffold(bottomBar = {
                AppBottomBar(
                    currentRoute = Routes.Todo,
                    onNavigate = { route -> navController.navigate(route) { launchSingleTop = true } }
                )
            }) { pad ->
                TodoScreen(modifier = Modifier.padding(pad))
            }
        }

        composable(Routes.Calendar) {
            Scaffold(bottomBar = {
                AppBottomBar(
                    currentRoute = Routes.Calendar,
                    onNavigate = { route -> navController.navigate(route) { launchSingleTop = true } }
                )
            }) { pad ->
                CalendarScreen(modifier = Modifier.padding(pad))
            }
        }

        composable(Routes.Notes) {
            Scaffold(bottomBar = {
                AppBottomBar(
                    currentRoute = Routes.Notes,
                    onNavigate = { route -> navController.navigate(route) { launchSingleTop = true } }
                )
            }) { pad ->
                NotesScreen(modifier = Modifier.padding(pad))
            }
        }

        composable(Routes.Profile) {
            Scaffold(bottomBar = {
                AppBottomBar(
                    currentRoute = Routes.Profile,
                    onNavigate = { route -> navController.navigate(route) { launchSingleTop = true } }
                )
            }) { pad ->
                ProfileScreen(
                    onBack = { navController.navigate(Routes.Dashboard) },
                    modifier = Modifier.padding(pad)
                )
            }
        }
    }
}
