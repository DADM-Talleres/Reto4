package com.example.triqui.logic

sealed class GameResult {
    data object InProgress: GameResult()
    data class Winner(val player: Player) : GameResult()
    data object Draw : GameResult()
}

class TicTacToeGame {
    private val winLines = listOf(
        listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
        listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
        listOf(0,4,8), listOf(2,4,6)
    )

    var board = Board()
        private set
    var currentPlayer = Player.X
        private set
    var result: GameResult = GameResult.InProgress
        private set

    fun playMove(index: Int): Boolean {
        if (result != GameResult.InProgress || board.cells[index] != null) return false

        board = board.placeMark(index, currentPlayer)
        result = evaluate()
        if (result == GameResult.InProgress) {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
        return true
    }

    fun reset(){
        board = Board()
        currentPlayer = Player.X
        result = GameResult.InProgress
    }

    private fun evaluate(): GameResult {
        for (line in winLines) {
            val (a, b, c) = line
            val v = board.cells[a]
            if (v != null && v == board.cells[b] && v == board.cells[c]){
                return GameResult.Winner(v)
            }
        }
        return if (board.isFull()) GameResult.Draw else GameResult.InProgress
    }
}