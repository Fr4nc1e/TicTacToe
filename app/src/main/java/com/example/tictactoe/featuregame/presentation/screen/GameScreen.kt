package com.example.tictactoe.featuregame.presentation.screen

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.core.ai.TicTacToeAI
import com.example.tictactoe.featuregame.presentation.screen.components.widgt.DifficultySelector
import com.example.tictactoe.featuregame.presentation.screen.components.widgt.TopBar
import com.example.tictactoe.featuregame.presentation.screen.orientation.LandscapeScreen
import com.example.tictactoe.featuregame.presentation.screen.orientation.PortraitScreen
import com.example.tictactoe.featuregame.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(
    onNavigateUp: () -> Unit = {},
    onExit: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    var orientation by remember { mutableStateOf(ORIENTATION_PORTRAIT) }
    val ticTacToeAI = TicTacToeAI(viewModel)

    DifficultySelector()

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(
            onNavigateUp = onNavigateUp,
            onExit = onExit
        )

        when (orientation) {
            ORIENTATION_LANDSCAPE -> {
                LandscapeScreen(
                    ticTacToeAI = ticTacToeAI
                )
            }
            ORIENTATION_PORTRAIT -> {
                PortraitScreen(
                    ticTacToeAI = ticTacToeAI
                )
            }
        }
    }
}
