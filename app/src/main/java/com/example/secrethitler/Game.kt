package com.example.secrethitler

import java.io.Serializable
import kotlin.Exception

class Game (private val playerNames: List<String>) : Serializable {
    private val players = mutableMapOf<String, Player>()
    private val deck = Deck()
    val playerCount = playerNames.size
    var liberalCardsCount = 0
    var fascistCardsCount = 0
    private var anarchyCount = 0

    private var nominatedChancellor: Player
    private var nominatedPresident: Player

    private var prevChancellor: Player
    private var prevPresident: Player

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

        val firstPlayer = players[playerNames[0]]!!
        nominatedPresident = firstPlayer
        nominatedChancellor = firstPlayer
        prevPresident = firstPlayer
        prevChancellor = firstPlayer
    }

    fun getFascists() : List<Player> {
        val fascists = mutableListOf<Player>()
        for ((name, player) in players) {
            if (player.getRole() == Role.FASCIST) {
                fascists.add(player)
            }
        }

        return fascists.toList()
    }

    fun getHitler() : Player {
        for ((name, player) in players) {
            if (player.getRole() == Role.HITLER) {
                return player
            }
        }
        throw Exception("WTF NO HITLER???")
    }

    fun addCard(article: Article) {
        if (article == Article.LIBERAL) {
            liberalCardsCount += 1
        }
        else {
            fascistCardsCount += 1
        }
    }

    fun increaseAnarchy() : Article? {
        anarchyCount += 1
        if (anarchyCount == 3) {
            anarchyCount = 0
            return deck.getCard()
        }

        return null
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

    fun getCards(): List<Article> {
        val cards = mutableListOf<Article>()

        for (i in 0 until 3) {
            cards.add(deck.getCard())
        }

        return cards.toList()
    }

    fun discardCard(card: Article) {
        deck.discardCard(card)
    }

    fun nominateNext() {
        val newPresidentName = playerNames[playerNames.indexOf(nominatedPresident.name) + 1]
        nominatedPresident = players[newPresidentName]!!
    }

    fun elect() {
        prevChancellor = nominatedChancellor
        prevPresident = nominatedPresident
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
                if (nominatedPresident != player && prevChancellor != player)
                    names.add(playerNames[i])
            }
            else {
                if (nominatedPresident != player && prevChancellor != player && prevPresident != player)
                    names.add(playerNames[i])
            }
        }

        return names
    }

    fun getPlayersForAction(): List<String> {
        val names = mutableListOf<String>()
        for (i in playerNames.indices) {
            val player = players[playerNames[i]]!!
                if (nominatedPresident != player)
                    names.add(playerNames[i])

        }

        return names
    }
}