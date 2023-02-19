package com.example.tictactoe.featuregame.presentation.event

sealed class GameEvent {
    object PlayAgainButtonClicked : GameEvent()
    object MenuButtonClicked : GameEvent()
    object DialogButtonClicked : GameEvent()
    object ConfirmDifficulty : GameEvent()
    object OnEasyClicked : GameEvent()
    object OnHarderClicked : GameEvent()
    object OnExpertClicked : GameEvent()
    object OnFirstClicked : GameEvent()
    object OnStartClicked : GameEvent()
    data class BoardTapped(val cellNo: Int) : GameEvent()
}
