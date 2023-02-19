package com.example.tictactoe

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.core.navigation.Screen
import com.example.tictactoe.featuregame.presentation.screen.GameScreen
import com.example.tictactoe.featurehome.presentation.screen.HomeScreen
import com.example.tictactoe.featuresetting.presentation.screen.SettingScreen

@Composable
fun HubApp() {
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
                onNavBack = {
                    navController.popBackStack(
                        route = Screen.Game.route,
                        inclusive = true
                    )
                }
            )
        }
        composable(Screen.Setting.route) {
            SettingScreen(
                onNavBack = {
                    navController.popBackStack(
                        route = Screen.Setting.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}
