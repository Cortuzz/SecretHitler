package com.example.secrethitler

import java.io.Serializable
import kotlin.Exception

class Game (private val playerNames: List<String>) : Serializable {
    private val players = mutableMapOf<String, Player>()
    private val deck = Deck()
    private var liberalCardsCount = 0
    private var fascistCardsCount = 0

    private lateinit var nominatedChancellor: Player
    private lateinit var nominatedPresident: Player

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

        nominatedPresident = players[playerNames[0]]!!
    }

    fun getPlayerNameByIndex(index: Int) : String {
        return playerNames[index]
    }

    fun getPlayerRole(playerName: String): Role {
        return players[playerName]!!.getRole()
    }

    fun getPlayersCount(): Int {
        return players.size
    }

    fun getPlayers(): List<String> {
        return playerNames
    }

    fun nominate(presidentName: String = "", chancellorName: String = "") {
        if (presidentName != "")
            nominatedPresident = players[presidentName]!!
        if (chancellorName != "")
            nominatedChancellor = players[chancellorName]!!
    }

    fun getPlayersForElection(): List<String> {
        val names = mutableListOf<String>()
        for (i in playerNames.indices) {
            val player = players[playerNames[i]]!!
            if (players.size <= 5) {
                if (player.post == Post.CITIZEN && player.lastPost == Post.CITIZEN)
                    names.add(playerNames[i])
            }
            else {
                if (player.post == Post.CITIZEN && player.lastPost != Post.CHANCELLOR)
                    names.add(playerNames[i])
            }
        }

        return names
    }
}