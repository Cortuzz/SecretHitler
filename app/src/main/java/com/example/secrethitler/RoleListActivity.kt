package com.example.secrethitler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.io.Serializable

class RoleListActivity : AppCompatActivity()
{
    private lateinit var game: Game
    private val rolesImagesIds = mapOf<Role, Int>(Role.LIBERAL to R.id.imageLiberal,
    Role.FASCIST to R.id.imageFascist, Role.HITLER to R.id.imageHitler)
    private val fascistsNamesIds = listOf(R.id.hitler, R.id.fascist1, R.id.fascist2, R.id.fascist3)
    private var fascistsNames: MutableList<Player> = mutableListOf()

    private var playerCount = 0
    private var isShowingCards = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_list)

        game = intent.getSerializableExtra("game")!! as Game
        fascistsNames = game.getFascists().toMutableList()
        fascistsNames.add(game.getHitler())
        fascistsNames.reverse()

        for (i in 0 until fascistsNames.size) {
            val name = fascistsNames[i].name
            val role = fascistsNames[i].getRole()
            findViewById<TextView>(fascistsNamesIds[i]).text = "$name - $role"
        }

        val playerName = game.getPlayerNameByIndex(playerCount)
        findViewById<TextView>(R.id.playerName).text = playerName +  " is"
    }

    private fun hideRoles() {
        for ((role, id) in rolesImagesIds) {
            findViewById<ImageView>(id).visibility = View.INVISIBLE
        }

        for (id in fascistsNamesIds) {
            findViewById<TextView>(id).visibility = View.INVISIBLE
        }
    }

    private fun showFascists() {
        for (id in fascistsNamesIds) {
            findViewById<TextView>(id).visibility = View.VISIBLE
        }
    }

    fun nextScreen(view: View) {
        if (playerCount == game.getPlayersCount()) {
            val gameActivityIntent = Intent(this, GameActivity::class.java)
            gameActivityIntent.putExtra("game", game)

            startActivity(gameActivityIntent)
            return
        }

        val playerName = game.getPlayerNameByIndex(playerCount)
        val playerRole = game.getPlayerRole(playerName)
        val roleImage = findViewById<ImageView>(rolesImagesIds[playerRole]!!)

        if (isShowingCards) {
            hideRoles()
            findViewById<TextView>(R.id.playerName).text = playerName +  " is"
            isShowingCards = false
        }
        else {
            if (playerRole == Role.FASCIST || playerRole == Role.HITLER && game.playerCount <= 6) {
                showFascists()
            }
            roleImage.visibility = View.VISIBLE
            findViewById<TextView>(R.id.playerName).text =
                playerName +  " is " + playerRole.toString()
            isShowingCards = true
            playerCount += 1
        }
    }
}