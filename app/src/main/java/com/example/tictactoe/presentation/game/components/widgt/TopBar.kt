package com.example.tictactoe.presentation.game.components.widgt

import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R
import com.example.tictactoe.presentation.game.GameEvent
import com.example.tictactoe.presentation.game.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onFinish: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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
}