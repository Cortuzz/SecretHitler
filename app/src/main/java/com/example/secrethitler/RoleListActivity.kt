package com.example.secrethitler

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

    private var playerCount = 0
    private var isShowingCards = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_list)

        game = intent.getSerializableExtra("game")!! as Game
        val playerName = game.getPlayerNameByIndex(playerCount)
        findViewById<TextView>(R.id.playerName).text = playerName +  " is"
    }

    fun hideRoles() {
        for ((role, id) in rolesImagesIds) {
            findViewById<ImageView>(id).visibility = View.INVISIBLE
        }
    }

    fun nextScreen(view: View) {
        val playerName = game.getPlayerNameByIndex(playerCount)
        val playerRole = game.getPlayerRole(playerName)
        val roleImage = findViewById<ImageView>(rolesImagesIds[playerRole]!!)

        if (isShowingCards) {
            hideRoles()
            findViewById<TextView>(R.id.playerName).text = playerName +  " is"
            isShowingCards = false
        }
        else {
            roleImage.visibility = View.VISIBLE
            findViewById<TextView>(R.id.playerName).text =
                playerName +  " is " + playerRole.toString()
            isShowingCards = true
            playerCount += 1
        }
    }
}