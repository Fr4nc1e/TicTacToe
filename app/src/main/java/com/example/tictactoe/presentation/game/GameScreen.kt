package com.example.tictactoe.presentation.game

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R
import com.example.tictactoe.presentation.components.Circle
import com.example.tictactoe.presentation.components.Cross
import com.example.tictactoe.presentation.components.GameBoard
import com.example.tictactoe.presentation.components.WinDiagonalLine1
import com.example.tictactoe.presentation.components.WinDiagonalLine2
import com.example.tictactoe.presentation.components.WinHorizontalLine1
import com.example.tictactoe.presentation.components.WinHorizontalLine2
import com.example.tictactoe.presentation.components.WinHorizontalLine3
import com.example.tictactoe.presentation.components.WinVerticalLine1
import com.example.tictactoe.presentation.components.WinVerticalLine2
import com.example.tictactoe.presentation.components.WinVerticalLine3

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    onFinish: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    if (viewModel.showDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(GameEvent.DialogButtonClicked) },
            confirmButton = {
                IconButton(onClick = { viewModel.onEvent(GameEvent.ConfirmDifficulty) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }},
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null
                )
            },
            title = { Text(text = stringResource(R.string.difficulty)) },
            text = {
                Column {
                    Text(text = stringResource(R.string.difficulty_text))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.easy))
                        Checkbox(
                            checked = viewModel.onEasyClick.value,
                            onCheckedChange = { viewModel.onEvent(GameEvent.onEasyClicked) },
                            enabled = !viewModel.onHarderClick.value && !viewModel.onExpertClick.value
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.harder))
                        Checkbox(
                            checked = viewModel.onHarderClick.value,
                            onCheckedChange = { viewModel.onEvent(GameEvent.onHarderClicked) },
                            enabled = !viewModel.onEasyClick.value && !viewModel.onExpertClick.value
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.expert))
                        Checkbox(
                            checked = viewModel.onExpertClick.value,
                            onCheckedChange = { viewModel.onEvent(GameEvent.onExpertClicked) },
                            enabled = !viewModel.onEasyClick.value && !viewModel.onHarderClick.value
                        )
                    }
                } },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.tic_tac_toe)) },
            navigationIcon = {
                IconButton(onClick = { onFinish() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { viewModel.onEvent(GameEvent.MenuButtonClicked) }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
                DropdownMenu(
                    expanded = viewModel.showMenu.value,
                    onDismissRequest = { viewModel.onEvent(GameEvent.MenuButtonClicked) }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.about)) },
                        onClick = {
                            val uri = Uri.parse("https://en.wikipedia.org/wiki/Tic-tac-toe")
                            val intents = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intents)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.difficulty)) },
                        onClick = {
                            viewModel.onEvent(GameEvent.DialogButtonClicked)
                        }
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.player_o) + "${state.playerCircleCount}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.draw) + "${state.drawCount}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.player_x) + "${state.playerCrossCount}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = stringResource(R.string.tic_tac_toe),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
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
                                        indication = null
                                    ) {
                                        viewModel.onEvent(
                                            GameEvent.BoardTapped(cellNo)
                                        )
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
                    onClick = { viewModel.onEvent(GameEvent.PlayAgainButtonClicked) },
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
