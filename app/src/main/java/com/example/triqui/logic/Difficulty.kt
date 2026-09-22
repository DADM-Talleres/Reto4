package com.example.triqui.logic

enum class Difficulty(val label: String) {
    EASY("Fácil"),
    MEDIUM("Medio"),
    HARD("Difícil");

    fun toStrategy(): MoveStrategy = when (this) {
        EASY -> RandomStrategy()
        MEDIUM -> MinimaxStrategy(mistakeChance = 0.4)
        HARD -> MinimaxStrategy(mistakeChance = 0.1)
    }
}