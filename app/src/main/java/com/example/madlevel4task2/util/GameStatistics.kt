package com.example.madlevel4task2.util

import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameResult

class GameStatistics(games: List<Game>) {
    var wins: Int
        private set
    var draws: Int
        private set
    var losses: Int
        private set

    init {
        wins = games.filter {
            it.result.equals(GameResult.WIN_PLAYER)
        }.size

        draws = games.filter {
            it.result.equals(GameResult.DRAW)
        }.size

        losses = games.size - wins - draws
    }

    fun add(game: Game) {
        when (game.result) {
            GameResult.WIN_PLAYER -> wins++
            GameResult.DRAW -> draws++
            GameResult.WIN_COMPUTER -> losses++
        }
    }
}