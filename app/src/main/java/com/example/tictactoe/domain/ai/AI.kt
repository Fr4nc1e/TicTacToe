package com.example.tictactoe.domain.ai

import com.example.tictactoe.data.BoardCellValue
import com.example.tictactoe.data.DifficultyLevel
import com.example.tictactoe.presentation.game.GameEvent
import com.example.tictactoe.presentation.game.GameResult
import com.example.tictactoe.presentation.game.GameViewModel
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class Ai @Inject constructor(
    private val viewModel: GameViewModel
) {
    private val difficultyLevel = viewModel.difficultyLevel.value

    fun makeMove() {
        val boardItems = viewModel.boardItems.toList()
        when (difficultyLevel) {
            DifficultyLevel.EASY -> makeRandomMove(boardItems)
            DifficultyLevel.MEDIUM -> makeMediumMove(boardItems)
            DifficultyLevel.HARD -> makeHardMove(boardItems)
        }
    }

    private fun makeRandomMove(boardItems: List<Pair<Int, BoardCellValue>>) {
        val emptyCells = boardItems.filter { it.second == BoardCellValue.NONE }
        if (!viewModel.hasBoardFull()) {
            val randomIndex = (emptyCells.indices - 1).random()
            val (cellNo, _) = emptyCells[randomIndex]
            viewModel.onEvent(GameEvent.BoardTapped(cellNo))
        }
    }

    private fun makeMediumMove(boardItems: List<Pair<Int, BoardCellValue>>) {
        val maxScore = mutableMapOf<Int, Int>()
        var bestMove = -1
        var maxScoreFound = Int.MIN_VALUE

        for (i in boardItems.indices - 3) {
            if (boardItems[i].second == BoardCellValue.NONE) {
                val score = minimax(boardItems, 0, false)
                maxScore[i] = score
                if (score > maxScoreFound) {
                    maxScoreFound = score
                    bestMove = i
                }
            }
        }

        if (bestMove != -1) {
            viewModel.onEvent(GameEvent.BoardTapped(boardItems[bestMove].first))
        }
    }


    private fun makeHardMove(boardItems: List<Pair<Int, BoardCellValue>>) {
        val maxScore = mutableMapOf<Int, Int>()
        var bestMove = -1
        var maxScoreFound = Int.MIN_VALUE

        for (i in boardItems.indices) {
            if (boardItems[i].second == BoardCellValue.NONE) {
                val score = minimax(boardItems, 0, false)
                maxScore[i] = score
                if (score > maxScoreFound) {
                    maxScoreFound = score
                    bestMove = i
                }
            }
        }

        if (bestMove != -1) {
            viewModel.onEvent(GameEvent.BoardTapped(boardItems[bestMove].first))
        }
    }

    private fun minimax(
        boardItems: List<Pair<Int, BoardCellValue>>,
        depth: Int,
        isMaximizing: Boolean
    ): Int {
        val result = viewModel.checkGameResult(boardItems.toMap())

        if (result != GameResult.INCOMPLETE) {
            return when (result) {
                GameResult.PLAYER_1_WIN -> 10 - depth
                GameResult.PLAYER_2_WIN -> depth - 10
                else -> 0
            }
        }

        val currentValue = if (isMaximizing) BoardCellValue.CROSS else BoardCellValue.CIRCLE
        var bestScore = if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE

        for (i in boardItems.indices) {
            if (boardItems[i].second == BoardCellValue.NONE) {
                val newBoardItems = boardItems.toMutableList()
                newBoardItems[i] = newBoardItems[i].copy(second = currentValue)
                val score = minimax(newBoardItems, depth + 1, !isMaximizing)
                bestScore = if (isMaximizing) {
                    max(bestScore, score)
                } else {
                    min(bestScore, score)
                }
            }
        }

        return bestScore
    }
}
