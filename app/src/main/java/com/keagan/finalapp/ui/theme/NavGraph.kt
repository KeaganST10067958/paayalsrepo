package com.keagan.finalapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keagan.finalapp.data.Repo
import com.keagan.finalapp.ui.theme.screens.*

@Composable
fun AppNavGraph(
    repo: Repo,                          // <-- Repo (not DataStoreRepository)
    startDestination: String = Routes.Splash
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.Splash) {
            SplashScreen(onDone = { navController.navigate(Routes.AfterSplash) })
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
                onSuccess = { navController.navigate(Routes.Dashboard) }
            )
        }

        composable(Routes.SignUp) {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onSuccess = { navController.navigate(Routes.Dashboard) }
            )
        }

        composable(Routes.Dashboard) {
            DashboardScreen(
                repo = repo,
                displayName = "Keagan",
                onNavSelect = { route -> navController.navigate(route) },
                onProfile = { navController.navigate(Routes.Profile) }
            )
        }

        composable(Routes.Calendar) { CalendarScreen(repo = repo) }
        composable(Routes.Notes)    { NotesScreen(repo = repo) }
        composable(Routes.Todo)     { TodoScreen(repo = repo) }
        composable(Routes.Profile)  { ProfileScreen(onBack = { navController.popBackStack() }) }
    }
}
