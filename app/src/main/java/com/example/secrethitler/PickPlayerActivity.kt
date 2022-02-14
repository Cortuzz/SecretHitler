package com.example.secrethitler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import java.lang.NullPointerException

class PickPlayerActivity : AppCompatActivity() {
    private lateinit var pickType: PickType
    private val playersIds = listOf(R.id.player1, R.id.player2, R.id.player3, R.id.player4,
        R.id.player5, R.id.player6, R.id.player7, R.id.player8, R.id.player9, R.id.player10)
    private lateinit var playersNames: Array<String>
    private lateinit var nameIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_player)

        playersNames = intent.getSerializableExtra("playersNames")!! as Array<String>
        pickType = intent.getSerializableExtra("pickType")!! as PickType

        for (i in playersNames.indices) {
            findViewById<Button>(playersIds[i]).hint = playersNames[i]
        }
        for (i in playersNames.size until 10) {
            val button = findViewById<Button>(playersIds[i])
            button.isEnabled = false
            button.isClickable = false
            button.isSoundEffectsEnabled = false
            button.visibility = View.INVISIBLE
        }
    }

    fun returnPlayerName(view: View) {
        lateinit var playerName: CharSequence
        try {
            playerName = findViewById<Button>(view.id).hint
        }
        catch (e: NullPointerException) {
            return
        }

        val nameIntent = Intent().apply { putExtra("playerName", playerName) }
        nameIntent.apply { putExtra("pickType", pickType) }
        setResult(RESULT_OK, nameIntent)
        finish()
    }
}