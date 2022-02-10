package com.example.secrethitler

import java.io.Serializable

class Deck : Serializable {
    private var deck = mutableListOf<Article>()
    private var usedDeck = mutableListOf<Article>()

    init {
        for (i in 0 until 11)
        {
            deck.add(Article.FASCIST)
        }

        for (i in 0 until 6)
        {
            deck.add(Article.LIBERAL)
        }

        deck.shuffle()
    }

    private fun checkDeckSize() {
        if (deck.size == 0) {
            deck = usedDeck.toMutableList()
            deck.shuffle()

            usedDeck.clear()
        }
    }

    fun getCard(): Article {
        checkDeckSize()
        return deck.removeAt(0)
    }

    fun discardCard(article: Article) {
        usedDeck.add(article)
    }
}