package com.example.tictactoe.featuregame.presentation.screen.orientation

import android.content.Context
import android.media.AudioManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R
import com.example.tictactoe.core.ai.TicTacToeAI
import com.example.tictactoe.featuregame.presentation.event.GameEvent
import com.example.tictactoe.featuregame.presentation.screen.components.basic.Circle
import com.example.tictactoe.featuregame.presentation.screen.components.basic.Cross
import com.example.tictactoe.featuregame.presentation.screen.components.basic.GameBoard
import com.example.tictactoe.featuregame.presentation.screen.components.widgt.DrawVictoryLine
import com.example.tictactoe.featuregame.presentation.screen.components.widgt.GameInfo
import com.example.tictactoe.featuregame.presentation.state.BoardCellValue
import com.example.tictactoe.featuregame.presentation.viewmodel.GameViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LandscapeScreen(
    viewModel: GameViewModel = hiltViewModel(),
    ticTacToeAI: TicTacToeAI
) {
    val state = viewModel.state.value
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            contentAlignment = Alignment.Center
        ) {
            GameBoard()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3)
            ) {
                viewModel.boardItems.forEach { (cellNo, boardCellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                    enabled = viewModel.startGame.value
                                ) {
                                    viewModel.onEvent(
                                        GameEvent.BoardTapped(cellNo)
                                    )
                                    ticTacToeAI.makeMove()
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = viewModel.boardItems[cellNo] != BoardCellValue.NONE,
                                enter = scaleIn(tween(500))
                            ) {
                                if (boardCellValue == BoardCellValue.CIRCLE) {
                                    Circle()
                                } else if (boardCellValue == BoardCellValue.CROSS) {
                                    Cross()
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.hasWon,
                    enter = fadeIn(tween(2000))
                ) {
                    DrawVictoryLine(state = state)
                    val audioManager = LocalContext.current.getSystemService(
                        Context.AUDIO_SERVICE
                    ) as AudioManager
                    audioManager.playSoundEffect(
                        AudioManager.FX_KEYPRESS_RETURN,
                        1.0f
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameInfo(state = state)
            Switch(
                checked = viewModel.whoGoesFirst.value,
                onCheckedChange = { viewModel.onEvent(GameEvent.OnFirstClicked) },
                thumbContent = {
                    Text(text = stringResource(R.string.ai))
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Green
                )
            )
            Button(
                onClick = {
                    viewModel.onEvent(GameEvent.OnStartClicked)
                    if (viewModel.whoGoesFirst.value) { ticTacToeAI.makeMove() }
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.start),
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {
                    viewModel.onEvent(GameEvent.PlayAgainButtonClicked)
                    if (viewModel.whoGoesFirst.value) {
                        ticTacToeAI.makeMove()
                    }
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.play_again),
                    fontSize = 16.sp
                )
            }
        }
    }
}
