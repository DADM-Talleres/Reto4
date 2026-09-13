package com.example.triqui.logic

class ComputerPlayer(
    val self: Player,
    private val mistakeChance: Double = 0.3 // 30% chance to play a random move instead
) {
    private val opponent = if (self == Player.X) Player.O else Player.X

    fun chooseMove(board: Board): Int {
        val available = board.cells.indices.filter { board.cells[it] == null }
        if (available.isEmpty()) return -1

        if (Math.random() < mistakeChance) {
            return available.random()
        }

        var bestScore = Int.MIN_VALUE
        var bestMove = available.first()
        for (index in available) {
            val newBoard = board.placeMark(index, self)
            val score = minimax(newBoard, depth = 1, isMaximizing = false)
            if (score > bestScore) {
                bestScore = score
                bestMove = index
            }
        }
        return bestMove
    }

    private fun minimax(board: Board, depth: Int, isMaximizing: Boolean): Int {
        val winner = winnerOf(board)
        if (winner == self) return 10 - depth
        if (winner == opponent) return depth - 10
        if (board.isFull()) return 0

        val available = board.cells.indices.filter { board.cells[it] == null }
        return if (isMaximizing) {
            available.maxOf { minimax(board.placeMark(it, self), depth + 1, false) }
        } else {
            available.minOf { minimax(board.placeMark(it, opponent), depth + 1, true) }
        }
    }

    private val winLines = listOf(
        listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
        listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
        listOf(0,4,8), listOf(2,4,6)
    )

    private fun winnerOf(board: Board): Player? {
        for (line in winLines) {
            val (a, b, c) = line
            val v = board.cells[a]
            if (v != null && v == board.cells[b] && v == board.cells[c]) return v
        }
        return null
    }
}