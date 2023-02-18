package com.example.tictactoe.presentation.game

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tictactoe.R
import com.example.tictactoe.presentation.game.data.BoardCellValue
import com.example.tictactoe.presentation.game.data.VictoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    private val _state = mutableStateOf(GameState())
    val state: State<GameState> = _state

    private val _boardItems: MutableMap<Int, BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,
    )
    val boardItems: Map<Int, BoardCellValue> = _boardItems

    private val _showMenu = mutableStateOf(false)
    val showMenu: State<Boolean> = _showMenu

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _onEasyClick = mutableStateOf(true)
    val onEasyClick: State<Boolean> = _onEasyClick

    private val _onHarderClick = mutableStateOf(false)
    val onHarderClick: State<Boolean> = _onHarderClick

    private val _onExpertClick = mutableStateOf(false)
    val onExpertClick: State<Boolean> = _onExpertClick

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.BoardTapped -> {
                addValueToBoard(event.cellNo)
            }
            GameEvent.PlayAgainButtonClicked -> {
                gameReset()
            }
            GameEvent.MenuButtonClicked -> {
                _showMenu.value = !_showMenu.value
            }
            GameEvent.DialogButtonClicked -> {
                _showDialog.value = !_showDialog.value
            }
            GameEvent.ConfirmDifficulty -> {
            }
            GameEvent.EasyLevelClicked -> {
                _onEasyClick.value = !_onEasyClick.value
            }
            GameEvent.ExpertLevelClicked -> {
                _onExpertClick.value = !_onExpertClick.value
            }
            GameEvent.HarderLevelClicked -> {
                _onHarderClick.value = !_onHarderClick.value
            }
        }
    }

    private fun gameReset() {
        boardItems.forEach { (i, _) ->
            _boardItems[i] = BoardCellValue.NONE
        }
        _state.value = _state.value.copy(
            hintText = application.applicationContext.getString(R.string.player_o_turn),
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
    }

    private fun aiTurn() {
        val emptyCells = mutableListOf<Int>()
        boardItems.forEach { (i, value) ->
            if (value == BoardCellValue.NONE) {
                emptyCells.add(i)
            }
        }
        val randomCell = emptyCells.random()
        addValueToBoard(randomCell)
    }

    private fun addValueToBoard(cellNo: Int) {
        if (boardItems[cellNo] != BoardCellValue.NONE) {
            return
        }
        if (_state.value.currentTurn == BoardCellValue.CIRCLE) {
            _boardItems[cellNo] = BoardCellValue.CIRCLE
            if (checkForVictory(BoardCellValue.CIRCLE)) {
                _state.value = _state.value.copy(
                    hintText = application.applicationContext.getString(R.string.player_o_won),
                    playerCircleCount = _state.value.playerCircleCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()) {
                _state.value = _state.value.copy(
                    hintText = application.applicationContext.getString(R.string.game_draw),
                    drawCount = _state.value.drawCount + 1
                )
            } else {
                _state.value = _state.value.copy(
                    hintText = application.applicationContext.getString(R.string.player_x_turn),
                    currentTurn = BoardCellValue.CROSS
                )
            }
        } else if (_state.value.currentTurn == BoardCellValue.CROSS) {
            _boardItems[cellNo] = BoardCellValue.CROSS
            if (checkForVictory(BoardCellValue.CROSS)) {
                _state.value = _state.value.copy(
                    hintText = application.applicationContext.getString(R.string.player_x_won),
                    playerCrossCount = _state.value.playerCrossCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    hasWon = true
                )
            } else if (hasBoardFull()) {
                _state.value = _state.value.copy(
                    hintText = application.applicationContext.getString(R.string.game_draw),
                    drawCount = _state.value.drawCount + 1
                )
            } else {
                _state.value = _state.value.copy(
                    hintText = application.applicationContext.getString(R.string.player_o_won),
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
    }

    private fun checkForVictory(boardValue: BoardCellValue): Boolean {
        when {
            boardItems[1] == boardValue && boardItems[2] == boardValue && boardItems[3] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.HORIZONTAL1)
                return true
            }
            boardItems[4] == boardValue && boardItems[5] == boardValue && boardItems[6] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.HORIZONTAL2)
                return true
            }
            boardItems[7] == boardValue && boardItems[8] == boardValue && boardItems[9] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.HORIZONTAL3)
                return true
            }
            boardItems[1] == boardValue && boardItems[4] == boardValue && boardItems[7] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.VERTICAL1)
                return true
            }
            boardItems[2] == boardValue && boardItems[5] == boardValue && boardItems[8] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.VERTICAL2)
                return true
            }
            boardItems[3] == boardValue && boardItems[6] == boardValue && boardItems[9] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.VERTICAL3)
                return true
            }
            boardItems[1] == boardValue && boardItems[5] == boardValue && boardItems[9] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.DIAGONAL1)
                return true
            }
            boardItems[3] == boardValue && boardItems[5] == boardValue && boardItems[7] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.DIAGONAL2)
                return true
            }
            else -> return false
        }
    }

    private fun hasBoardFull(): Boolean {
        if (boardItems.containsValue(BoardCellValue.NONE)) return false
        return true
    }
}
