package com.example.tictactoe.presentation.game

sealed class GameEvent {
    object PlayAgainButtonClicked: GameEvent()
    object MenuButtonClicked: GameEvent()
    object DialogButtonClicked: GameEvent()
    object ConfirmDifficulty: GameEvent()
    object onEasyClicked: GameEvent()
    object onHarderClicked: GameEvent()
    object onExpertClicked: GameEvent()
    data class BoardTapped(val cellNo: Int): GameEvent()
}