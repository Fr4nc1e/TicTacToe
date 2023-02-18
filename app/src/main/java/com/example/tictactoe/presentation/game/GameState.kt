package com.example.tictactoe.presentation.game

import com.example.tictactoe.presentation.game.data.BoardCellValue
import com.example.tictactoe.presentation.game.data.VictoryType

data class GameState(
    val playerCircleCount: Int = 0,
    val playerCrossCount: Int = 0,
    val drawCount: Int = 0,
    val hintText: String = "Player 'O' turn",
    val currentTurn: BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType: VictoryType = VictoryType.NONE,
    val hasWon: Boolean = false
)
