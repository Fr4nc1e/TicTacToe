package com.example.tictactoe.presentation.game

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R
import com.example.tictactoe.data.BoardCellValue
import com.example.tictactoe.domain.ai.Ai
import com.example.tictactoe.presentation.game.components.basic.Circle
import com.example.tictactoe.presentation.game.components.basic.Cross
import com.example.tictactoe.presentation.game.components.basic.GameBoard
import com.example.tictactoe.presentation.game.components.widgt.DifficultySelector
import com.example.tictactoe.presentation.game.components.widgt.DrawVictoryLine
import com.example.tictactoe.presentation.game.components.widgt.GameInfo
import com.example.tictactoe.presentation.game.components.widgt.TopBar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    onFinish: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val ai = Ai(viewModel)

    DifficultySelector()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(onFinish = onFinish)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            GameInfo(state = state)

            Checkbox(
                checked = viewModel.whoGoesFirst.value,
                onCheckedChange = { viewModel.onEvent(GameEvent.OnFirstClicked) }
            )
            Button(onClick = {
                viewModel.onEvent(GameEvent.OnStartClicked)
                if (!viewModel.whoGoesFirst.value) {
                    ai.makeMove()
                }
            }) {
                Text(text = "Start")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                                        ai.makeMove()
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
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.hintText,
                    fontSize = 24.sp,
                )
                Button(
                    onClick = {
                        viewModel.onEvent(GameEvent.PlayAgainButtonClicked)
                        if (!viewModel.whoGoesFirst.value) {
                            ai.makeMove()
                        } },
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
}
