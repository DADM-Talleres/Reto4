package com.example.triqui.logic

data class Board(val cells: List<Player?> = List(9) {null}) {
    fun placeMark(index: Int, player: Player): Board {
        return copy(cells = cells.toMutableList().also { it[index] = player})
    }
    fun isFull(): Boolean = cells.none { it == null }
}
