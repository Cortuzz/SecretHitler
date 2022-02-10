package com.example.secrethitler

import java.io.Serializable
import java.lang.Exception

class Game (private val playerNames: List<String>) : Serializable {
    private lateinit var players: MutableMap<String, Player>
    private val deck = Deck()

    private val roles = listOf(Role.HITLER, Role.FASCIST, Role.LIBERAL, Role.LIBERAL,
                                Role.LIBERAL, Role.LIBERAL, Role.FASCIST, Role.LIBERAL,
                                Role.FASCIST, Role.LIBERAL)

    init {
        if (playerNames.size < 5) {
            throw Exception("At least 5 people needed for start the game.")
        }

        val gameRoles = roles.toMutableList().subList(0, playerNames.size)
        gameRoles.shuffle()

        for (i in playerNames.indices) {
            val player = Player(playerNames[i], gameRoles[i])
            players[playerNames[i]] = player
        }
    }

    fun getPlayerRole(playerName: String): Role {
        return players[playerName]!!.getRole()
    }
}