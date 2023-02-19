package com.example.tictactoe.featuregame.presentation.screen.components.widgt

import androidx.compose.runtime.Composable
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinDiagonalLine1
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinDiagonalLine2
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinHorizontalLine1
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinHorizontalLine2
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinHorizontalLine3
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinVerticalLine1
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinVerticalLine2
import com.example.tictactoe.featuregame.presentation.screen.components.basic.WinVerticalLine3
import com.example.tictactoe.featuregame.presentation.state.GameState
import com.example.tictactoe.featuregame.presentation.state.VictoryType

@Composable
fun DrawVictoryLine(
    state: GameState
) {
    when (state.victoryType) {
        VictoryType.HORIZONTAL1 -> WinHorizontalLine1()
        VictoryType.HORIZONTAL2 -> WinHorizontalLine2()
        VictoryType.HORIZONTAL3 -> WinHorizontalLine3()
        VictoryType.VERTICAL1 -> WinVerticalLine1()
        VictoryType.VERTICAL2 -> WinVerticalLine2()
        VictoryType.VERTICAL3 -> WinVerticalLine3()
        VictoryType.DIAGONAL1 -> WinDiagonalLine1()
        VictoryType.DIAGONAL2 -> WinDiagonalLine2()
        VictoryType.NONE -> {}
    }
}
