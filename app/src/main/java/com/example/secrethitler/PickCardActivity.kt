package com.example.secrethitler

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PickCardActivity : AppCompatActivity() {
    private val buttonsIds = listOf(R.id.article1, R.id.article2, R.id.article3)
    private val crossesIds = listOf(R.id.cross1, R.id.cross2, R.id.cross3)
    private var pickedCardIndex = -1
    private var isShowingCards = false
    private lateinit var articles: MutableList<Article>
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_card)

        articles = (intent.getSerializableExtra("articles")!! as Array<Article>).toMutableList()
        game = (intent.getSerializableExtra("game")!! as Game)
    }

    private fun changeVisibility(view: View, state: Boolean) {
        view.isEnabled = state
        view.isClickable = state
        view.isSoundEffectsEnabled = state

        if (state)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.INVISIBLE
    }

    private fun showCards() {
        if (articles.size == 2) {
            changeVisibility(findViewById<ImageButton>(R.id.article3), false)
        }

        for (i in articles.indices) {
            val article = articles[i]
            val button = findViewById<ImageButton>(buttonsIds[i])

            if (article == Article.LIBERAL) {
                button.setImageResource(R.drawable.liberal_article)
            }
            else {
                button.setImageResource(R.drawable.fascist_article)
            }
            changeVisibility(button, true)
        }
    }

    fun pickCard(view: View) {
        if (pickedCardIndex != -1) {
            findViewById<ImageView>(crossesIds[pickedCardIndex]).visibility = View.INVISIBLE
        }

        val cardIndex = buttonsIds.indexOf(view.id)
        if (cardIndex == pickedCardIndex) {
            pickedCardIndex = -1
            return
        }

        pickedCardIndex = cardIndex
        findViewById<ImageView>(crossesIds[pickedCardIndex]).visibility = View.VISIBLE
    }

    fun nextAction(view: View) {
        if (pickedCardIndex == -1 && isShowingCards) {
            val toastText = "Pick a card to discard!"
            Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
            return
        }

        isShowingCards = if (isShowingCards) {
            for (id in buttonsIds) {
                changeVisibility(findViewById<ImageButton>(id), false)
            }
            for (id in crossesIds) {
                changeVisibility(findViewById<ImageView>(id), false)
            }
            game.discardCard(articles.removeAt(pickedCardIndex))
            pickedCardIndex = -1

            if (articles.size == 1) {
                returnIntent()
            }
            false
        } else {
            showCards()
            true
        }
    }

    private fun returnIntent() {
        val cardIntent = Intent().apply { putExtra("article", articles[0]) }
        cardIntent.apply { putExtra("game", game) }
        setResult(RESULT_CANCELED, cardIntent)
        finish()
    }
}