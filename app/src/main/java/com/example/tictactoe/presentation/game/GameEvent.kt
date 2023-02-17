package com.example.tictactoe.presentation.game

sealed class GameEvent {
    object PlayAgainButtonClicked: GameEvent()
    data class BoardTapped(val cellNo: Int): GameEvent()
}