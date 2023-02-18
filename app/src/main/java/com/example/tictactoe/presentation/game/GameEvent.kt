package com.example.tictactoe.presentation.game

sealed class GameEvent {
    object PlayAgainButtonClicked: GameEvent()
    object MenuButtonClicked: GameEvent()
    object DialogButtonClicked: GameEvent()
    object ConfirmDifficulty: GameEvent()
    object EasyLevelClicked: GameEvent()
    object HarderLevelClicked: GameEvent()
    object ExpertLevelClicked: GameEvent()
    data class BoardTapped(val cellNo: Int): GameEvent()
}