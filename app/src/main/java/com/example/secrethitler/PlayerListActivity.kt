package com.example.secrethitler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.io.Serializable


class PlayerListActivity : AppCompatActivity()
{
    private lateinit var playerNamesIds: List<Int>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        playerNamesIds = listOf<Int>(R.id.player1, R.id.player2, R.id.player3, R.id.player4,
        R.id.player5, R.id.player6, R.id.player7, R.id.player8, R.id.player9, R.id.player10)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)
    }

    fun startGame(view: View)
    {
        val playerNames = mutableListOf<String>()

        for (id in playerNamesIds) {
            val playerName = findViewById<EditText>(id).text.toString()
            // todo: Create checking for same names

            if (playerName.replace(" ", "") != "") {
                playerNames.add(playerName)
            }
        }

        if (playerNames.size < 5) {
            val toastText = "At least 5 people needed for start the game."
            Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()

            return
        }

        val game = Game(playerNames)

        val roleListActivityIntent = Intent(this, RoleListActivity::class.java)
        roleListActivityIntent.putExtra("game", game)

        startActivity(roleListActivityIntent)
    }
}