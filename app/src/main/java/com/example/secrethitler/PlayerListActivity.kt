package com.example.secrethitler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PlayerListActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)
    }

    fun startGame(view: View)
    {
        startActivity(Intent(this, RoleListActivity::class.java))
        val a = 3
    }
}