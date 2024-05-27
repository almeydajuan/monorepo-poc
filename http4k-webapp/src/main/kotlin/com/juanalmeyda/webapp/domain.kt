package com.juanalmeyda.webapp

import com.juanalmeyda.webapp.Player.O
import com.juanalmeyda.webapp.Player.X

data class Game(val moves: List<Move> = emptyList()) {
    val winner: Player? = findWinner()

    fun makeMove(x: Int, y: Int): Game {
        if (winner != null) return this
        val nextPlayer = if (moves.lastOrNull()?.player != X) X else O
        return copy(moves = moves + Move(x, y, nextPlayer))
    }

    private fun findWinner(): Player? =
        enumValues<Player>().find { player ->
            moves.containsAll((0..2).map { Move(it, 0, player) }) ||
                moves.containsAll((0..2).map { Move(it, 1, player) }) ||
                moves.containsAll((0..2).map { Move(it, 2, player) }) ||
                moves.containsAll((0..2).map { Move(0, it, player) }) ||
                moves.containsAll((0..2).map { Move(1, it, player) }) ||
                moves.containsAll((0..2).map { Move(2, it, player) }) ||
                moves.containsAll((0..2).map { Move(it, it, player) }) ||
                moves.containsAll((0..2).map { Move(it, 2 - it, player) })
        }
}

data class Move(val x: Int, val y: Int, val player: Player)

enum class Player { X, O }
