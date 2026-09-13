package com.example.triqui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triqui.logic.ComputerPlayer
import com.example.triqui.logic.GameResult
import com.example.triqui.logic.Player
import com.example.triqui.logic.TicTacToeGame
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

data class GameUiState(
    val cells: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val statusText: String = "Turno de X"
)

class GameViewModel : ViewModel() {
    private val game = TicTacToeGame()
    private val human = Player.X
    private val computer = ComputerPlayer(self = Player.O)

    private val _uiState = MutableStateFlow(toUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onCellClicked(index: Int) {
        if (game.currentPlayer != human) return // ignore taps during computer's turn
        if (!game.playMove(index)) return

        _uiState.value = toUiState()

        if (game.result == GameResult.InProgress && game.currentPlayer == computer.self) {
            makeComputerMove()
        }
    }

    private fun makeComputerMove() {
        viewModelScope.launch {
            delay(400.milliseconds) // small pause so it doesn't feel instant/robotic
            val move = computer.chooseMove(game.board)
            if (move >= 0) {
                game.playMove(move)
                _uiState.value = toUiState()
            }
        }
    }


    fun onResetClicked() {
        game.reset()
        _uiState.value = toUiState()
    }

    private fun toUiState(): GameUiState {
        val status = when (val r = game.result) {
            is GameResult.InProgress ->
                if (game.currentPlayer == human) "Tu turno" else "Turno del computador"
            is GameResult.Winner ->
                if (r.player == human) "Ganaste!" else "El computador gana!"
            is GameResult.Draw -> "Empate!"
        }
        return GameUiState(
            cells = game.board.cells,
            currentPlayer = game.currentPlayer,
            statusText = status
        )
    }
}