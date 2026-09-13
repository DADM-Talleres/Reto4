package com.example.triqui.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.triqui.logic.Player

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.statusText, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Cell(mark = state.cells[index]) { viewModel.onCellClicked(index) }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = viewModel::onResetClicked) {Text("Reiniciar")}
    }
}

@Composable
fun Cell(mark: Player?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .border(1.dp, Color.Black)
            .clickable(enabled = mark == null) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = mark?.name ?: "", style = MaterialTheme.typography.headlineMedium)
    }
}