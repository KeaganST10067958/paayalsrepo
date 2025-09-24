package com.keagan.finalapp.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.*
import com.keagan.finalapp.data.Repo
import com.keagan.finalapp.ui.theme.screens.*

@Composable
fun AppNavGraph(repo: Repo, startDestination: String = Routes.Splash) {
    val navController = rememberNavController()

    NavHost(navController, startDestination) {

        composable(Routes.Splash) {
            SplashScreen { navController.navigate(Routes.AfterSplash) { popUpTo(Routes.Splash) { inclusive = true } } }
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
                    navController.navigate(Routes.Dashboard) { popUpTo(Routes.AfterSplash) { inclusive = true } }
                }
            )
        }

        composable(Routes.SignUp) {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Routes.Dashboard) { popUpTo(Routes.AfterSplash) { inclusive = true } }
                }
            )
        }

        // MAIN TABS
        composable(Routes.Dashboard) {
            Scaffold(
                bottomBar = {
                    AppBottomBar(
                        currentRoute = Routes.Dashboard,
                        onNavigate = { r -> navController.navigate(r) { launchSingleTop = true } }
                    )
                }
            ) { pad ->
                DashboardScreen(
                    displayName = "Keagan",
                    modifier = Modifier.padding(pad),
                    onOpenTodo = { navController.navigate(Routes.Todo) },
                    onOpenCalendar = { navController.navigate(Routes.Calendar) },
                    onOpenNotes = { navController.navigate(Routes.Notes) }
                )
            }
        }



        composable(Routes.Todo) {
            Scaffold(bottomBar = {
                AppBottomBar(Routes.Todo) { route -> navController.navigate(route) { launchSingleTop = true } }
            }) { pad ->
                TodoScreen(repo = repo, modifier = Modifier.padding(pad))
            }
        }

        composable(Routes.Calendar) {
            Scaffold(bottomBar = {
                AppBottomBar(Routes.Calendar) { route -> navController.navigate(route) { launchSingleTop = true } }
            }) { pad ->
                CalendarScreen(repo = repo, modifier = Modifier.padding(pad))
            }
        }

        composable(Routes.Notes) {
            Scaffold(bottomBar = {
                AppBottomBar(Routes.Notes) { route -> navController.navigate(route) { launchSingleTop = true } }
            }) { pad ->
                NotesScreen(repo = repo, modifier = Modifier.padding(pad))
            }
        }

        composable(Routes.Profile) {
            Scaffold(bottomBar = {
                AppBottomBar(Routes.Profile) { route -> navController.navigate(route) { launchSingleTop = true } }
            }) { pad ->
                ProfileScreen(onBack = { navController.navigate(Routes.Dashboard) }, modifier = Modifier.padding(pad))
            }
        }
    }
}
