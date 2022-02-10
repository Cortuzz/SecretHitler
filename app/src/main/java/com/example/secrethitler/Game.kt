package com.example.secrethitler

import java.io.Serializable

class Game (val playerNames: List<String>) : Serializable {
    lateinit var players: Map<String, Player>
    private val deck = Deck()
}