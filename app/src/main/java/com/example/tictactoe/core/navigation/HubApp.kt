package com.example.tictactoe.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.featuregame.presentation.screen.GameScreen
import com.example.tictactoe.featurehome.presentation.screen.HomeScreen

@Composable
fun HubApp(
    onExit: () -> Unit = {}
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = navController::navigate
            )
        }
        composable(Screen.Game.route) {
            GameScreen(
                onNavigateUp = {
                    navController.popBackStack(
                        route = Screen.Game.route,
                        inclusive = true
                    )
                },
                onExit = onExit
            )
        }
    }
}
