package com.example.mybaskettrainer.data.local

import com.example.mybaskettrainer.data.model.Scoreboard

object ScoreboardStorage {
    private var scoreboard = Scoreboard()

    fun getScoreboard(): Scoreboard = scoreboard

    fun updateHomeScore(newScore: Int) {
        scoreboard = scoreboard.copy(homeScore = maxOf(0, newScore))
    }
    fun updateAwayScore(newScore: Int) {
        scoreboard = scoreboard.copy(awayScore = maxOf(0, newScore))
    }
    fun resetScoreboard() {
        scoreboard = Scoreboard()
    }
}