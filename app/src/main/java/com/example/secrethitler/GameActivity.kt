package com.example.secrethitler

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.lang.Exception

class GameActivity : AppCompatActivity() {
    private lateinit var game: Game
    private var activityLauncher: ActivityResultLauncher<Intent>? = null
    private var roundStage = Stage.ELECTION

    private val liberalArticlesIds =
        mutableListOf<Int>(R.id.liberalArticle1, R.id.liberalArticle2, R.id.liberalArticle3,
            R.id.liberalArticle4, R.id.liberalArticle5)
    private val fascistArticlesIds =
        mutableListOf<Int>(R.id.fascistArticle1, R.id.fascistArticle2, R.id.fascistArticle2,
            R.id.fascistArticle3, R.id.fascistArticle4, R.id.fascistArticle5, R.id.fascistArticle6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        activityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if (it.resultCode == RESULT_OK) {
                    val name = it.data?.getSerializableExtra("playerName") as String
                    val type = it.data?.getSerializableExtra("pickType") as PickType
                    defineRequest(name, type)
                }
                else if (it.resultCode == RESULT_CANCELED) {
                    val card = it.data?.getSerializableExtra("article") as Article
                    game = it.data?.getSerializableExtra("game") as Game
                    placeNextCard(card)
                    checkPresidentAction(card)
                }
            }

        game = intent.getSerializableExtra("game")!! as Game
    }

    private fun defineRequest(name: String, type: PickType) {
        when (type) {
            PickType.ELECTION -> game.nominate(chancellorName = name)
            PickType.ACTION -> null
        }
    }

    private fun placeNextCard(type: Article) {
        game.addCard(type)
        if (type == Article.LIBERAL) {
            findViewById<ImageView>(liberalArticlesIds.removeAt(0)).visibility = View.VISIBLE
            return
        }

        findViewById<ImageView>(fascistArticlesIds.removeAt(0)).visibility = View.VISIBLE
    }

    private fun draft() {
        game.elect()
        var articles = game.getCards().toTypedArray()

        val cardPickerIntent = Intent(this@GameActivity, PickCardActivity::class.java)
        cardPickerIntent.putExtra("articles", articles)
        cardPickerIntent.putExtra("game", game)
        activityLauncher?.launch(cardPickerIntent)
    }

    fun skipElection(view: View) {
        game.nominateNext()

        val skipButton = findViewById<Button>(R.id.buttonSkip)
        val nextButton = findViewById<Button>(R.id.buttonNext)

        skipButton.visibility = View.INVISIBLE
        skipButton.isEnabled = false
        nextButton.text = "N\nE\nX\nT"

        roundStage = Stage.ELECTION

        val anarchyArticle = game.increaseAnarchy()
        if (anarchyArticle != null)
            placeNextCard(anarchyArticle)
    }

    private fun checkPresidentAction(article: Article) {
        if (article == Article.LIBERAL ||
            (game.fascistCardsCount < 3 && game.playerCount <= 6) ||
            (game.fascistCardsCount < 2 && game.playerCount <= 8))
        {
            game.nominateNext()
            return
        }

        val playersNames = game.getPlayersForAction().toTypedArray()

        val playerPickerIntent = Intent(this@GameActivity, PickPlayerActivity::class.java)
        playerPickerIntent.putExtra("playersNames", playersNames)
        playerPickerIntent.putExtra("pickType", PickType.ACTION)
        activityLauncher?.launch(playerPickerIntent)
    }

    private fun election() {
        val skipButton = findViewById<Button>(R.id.buttonSkip)
        val nextButton = findViewById<Button>(R.id.buttonNext)

        skipButton.visibility = View.VISIBLE
        skipButton.isEnabled = true
        nextButton.text = "E\nL\nE\nC\nT"

        var playersNames = game.getPlayersForElection().toTypedArray()

        val playerPickerIntent = Intent(this@GameActivity, PickPlayerActivity::class.java)
        playerPickerIntent.putExtra("playersNames", playersNames)
        playerPickerIntent.putExtra("pickType", PickType.ELECTION)
        activityLauncher?.launch(playerPickerIntent)
    }

    private fun changeStage() {
        if (roundStage != Stage.ELECTION) {
            val skipButton = findViewById<Button>(R.id.buttonSkip)
            val nextButton = findViewById<Button>(R.id.buttonNext)

            skipButton.visibility = View.INVISIBLE
            skipButton.isEnabled = false
            nextButton.text = "N\nE\nX\nT"
        }

        roundStage = when (roundStage) {
            Stage.ELECTION -> Stage.DRAFT
            Stage.DRAFT -> Stage.ELECTION
            else -> {throw Exception()}
        }

    }

    fun nextAction(view: View) {
        when (roundStage) {
            Stage.ELECTION -> election()
            Stage.DRAFT -> draft()
        }

        changeStage()
    }
}