package com.example.tictactoe.featuregame.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.FX_KEY_CLICK
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tictactoe.R
import com.example.tictactoe.featuregame.presentation.event.GameEvent
import com.example.tictactoe.featuregame.presentation.state.BoardCellValue
import com.example.tictactoe.featuregame.presentation.state.DifficultyLevel
import com.example.tictactoe.featuregame.presentation.state.GameResult
import com.example.tictactoe.featuregame.presentation.state.GameState
import com.example.tictactoe.featuregame.presentation.state.VictoryType
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
        9 to BoardCellValue.NONE
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

    private val _difficultyLevel = mutableStateOf(DifficultyLevel.EASY)
    val difficultyLevel: State<DifficultyLevel> = _difficultyLevel

    private val _whoGoesFirst = mutableStateOf(false)
    val whoGoesFirst: State<Boolean> = _whoGoesFirst

    private val _startGame = mutableStateOf(false)
    val startGame: State<Boolean> = _startGame

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.BoardTapped -> {
                addValueToBoard(event.cellNo)
                val audioManager = application.applicationContext.getSystemService(
                    Context.AUDIO_SERVICE
                ) as AudioManager
                audioManager.playSoundEffect(
                    FX_KEY_CLICK,
                    1.0f
                )
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
                judgeDifficulty()
            }
            GameEvent.OnEasyClicked -> {
                _onEasyClick.value = !_onEasyClick.value
            }
            GameEvent.OnExpertClicked -> {
                _onExpertClick.value = !_onExpertClick.value
            }
            GameEvent.OnHarderClicked -> {
                _onHarderClick.value = !_onHarderClick.value
            }
            GameEvent.OnFirstClicked -> {
                _whoGoesFirst.value = !_whoGoesFirst.value
            }
            GameEvent.OnStartClicked -> {
                _startGame.value = !_startGame.value
            }
        }
    }

    private fun judgeDifficulty() {
        when {
            _onEasyClick.value -> {
                _difficultyLevel.value = DifficultyLevel.EASY
            }
            _onHarderClick.value -> {
                _difficultyLevel.value = DifficultyLevel.MEDIUM
            }
            _onExpertClick.value -> {
                _difficultyLevel.value = DifficultyLevel.HARD
            }
        }
    }

    private fun gameReset() {
        boardItems.forEach { (i, _) ->
            _boardItems[i] = BoardCellValue.NONE
        }
        _state.value = _state.value.copy(
            hintText = "",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
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
                    hintText = application.applicationContext.getString(R.string.player_o_turn),
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
    }

    private fun checkForVictory(boardValue: BoardCellValue): Boolean {
        when {
            boardItems[1] == boardValue &&
                boardItems[2] == boardValue &&
                boardItems[3] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.HORIZONTAL1)
                return true
            }
            boardItems[4] == boardValue &&
                boardItems[5] == boardValue &&
                boardItems[6] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.HORIZONTAL2)
                return true
            }
            boardItems[7] == boardValue &&
                boardItems[8] == boardValue &&
                boardItems[9] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.HORIZONTAL3)
                return true
            }
            boardItems[1] == boardValue &&
                boardItems[4] == boardValue &&
                boardItems[7] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.VERTICAL1)
                return true
            }
            boardItems[2] == boardValue &&
                boardItems[5] == boardValue &&
                boardItems[8] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.VERTICAL2)
                return true
            }
            boardItems[3] == boardValue &&
                boardItems[6] == boardValue &&
                boardItems[9] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.VERTICAL3)
                return true
            }
            boardItems[1] == boardValue &&
                boardItems[5] == boardValue &&
                boardItems[9] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.DIAGONAL1)
                return true
            }
            boardItems[3] == boardValue &&
                boardItems[5] == boardValue &&
                boardItems[7] == boardValue -> {
                _state.value = _state.value.copy(victoryType = VictoryType.DIAGONAL2)
                return true
            }
            else -> return false
        }
    }

    fun hasBoardFull(): Boolean {
        if (boardItems.containsValue(BoardCellValue.NONE)) return false
        return true
    }

    fun checkGameResult(boardItems: Map<Int, BoardCellValue>): GameResult {
        // Check for horizontal wins
        for (i in 1..7 step 3) {
            if (boardItems[i] != BoardCellValue.NONE &&
                boardItems[i] == boardItems[i + 1] &&
                boardItems[i] == boardItems[i + 2]
            ) {
                return if (boardItems[i] == BoardCellValue.CROSS) {
                    GameResult.PLAYER_O_WIN
                } else {
                    GameResult.PLAYER_X_WIN
                }
            }
        }

        // Check for vertical wins
        for (i in 1..3) {
            if (boardItems[i] != BoardCellValue.NONE &&
                boardItems[i] == boardItems[i + 3] &&
                boardItems[i] == boardItems[i + 6]
            ) {
                return if (boardItems[i] == BoardCellValue.CROSS) {
                    GameResult.PLAYER_O_WIN
                } else {
                    GameResult.PLAYER_X_WIN
                }
            }
        }

        // Check for diagonal wins
        if (boardItems[1] != BoardCellValue.NONE &&
            boardItems[1] == boardItems[5] &&
            boardItems[1] == boardItems[9]
        ) {
            return if (boardItems[1] == BoardCellValue.CROSS) {
                GameResult.PLAYER_O_WIN
            } else {
                GameResult.PLAYER_X_WIN
            }
        }

        if (boardItems[3] != BoardCellValue.NONE &&
            boardItems[3] == boardItems[5] &&
            boardItems[3] == boardItems[7]
        ) {
            return if (boardItems[3] == BoardCellValue.CROSS) {
                GameResult.PLAYER_O_WIN
            } else {
                GameResult.PLAYER_X_WIN
            }
        }

        // Check if there are still empty cells
        if (boardItems.containsValue(BoardCellValue.NONE)) {
            return GameResult.INCOMPLETE
        }

        // If all cells are filled and there is no winner, it is a tie.
        return GameResult.DRAW
    }
}
