package com.example.tictactoe.presentation.game.components.widgt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R
import com.example.tictactoe.presentation.game.GameEvent
import com.example.tictactoe.presentation.game.GameViewModel

@Composable
fun DifficultySelector(
    viewModel: GameViewModel = hiltViewModel()
) {
    if (viewModel.showDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(GameEvent.DialogButtonClicked) },
            confirmButton = {
                IconButton(onClick = { viewModel.onEvent(GameEvent.ConfirmDifficulty) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            },
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
}