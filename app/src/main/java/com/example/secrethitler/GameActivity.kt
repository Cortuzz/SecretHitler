package com.example.secrethitler

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class GameActivity : AppCompatActivity() {
    private lateinit var game: Game
    private var playerName = ""
    private var pickPlayerActivityLauncher: ActivityResultLauncher<Intent>? = null
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

        pickPlayerActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if (it.resultCode == RESULT_OK) {
                    playerName = it.data?.getSerializableExtra("playerName") as String
                }
            }

        game = intent.getSerializableExtra("game")!! as Game
    }

    private fun showNextCard(type: Article) {
        if (type == Article.LIBERAL) {
            findViewById<ImageView>(liberalArticlesIds.removeAt(0)).visibility = View.VISIBLE
            return
        }

        findViewById<ImageView>(fascistArticlesIds.removeAt(0)).visibility = View.VISIBLE
    }

    private fun elect() {
        var playersNames = game.getPlayers()

        val playerPickerIntent = Intent(this@GameActivity, PickPlayerActivity::class.java)
        playerPickerIntent.putExtra("playersNames", playersNames)
        pickPlayerActivityLauncher?.launch(playerPickerIntent)
    }

    fun nextAction(view: View) {
        when (roundStage) {
            Stage.ELECTION -> elect()
            Stage.VOTING -> null
            Stage.DRAFT -> null
        }
    }
}