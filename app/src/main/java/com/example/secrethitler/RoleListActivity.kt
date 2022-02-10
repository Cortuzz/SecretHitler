package com.example.secrethitler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.view.View
import android.widget.TextView
import java.io.Serializable

class RoleListActivity : AppCompatActivity()
{
    private lateinit var game: Game
    private var playerCount = 0
    private var isShowingCards = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_list)

        game = intent.getSerializableExtra("game")!! as Game
        val playerName = game.getPlayerNameByIndex(playerCount)
        findViewById<TextView>(R.id.playerName).text = playerName +  "'s is"
    }

    fun nextScreen(view: View) {
        val playerName = game.getPlayerNameByIndex(playerCount)
        val playerRole = game.getPlayerRole(playerName)

        if (isShowingCards) {
            findViewById<TextView>(R.id.playerName).text = playerName +  "'s is"
            isShowingCards = false
        }
        else {
            findViewById<TextView>(R.id.playerName).text =
                playerName +  "'s is " + playerRole.toString()
            isShowingCards = true
            playerCount += 1
        }
    }
}