package com.example.tictactoe.core.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Game : Screen("game")
}
