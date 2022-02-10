package com.example.secrethitler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGame(view: View)
    {
        startActivity(Intent(this, PlayerListActivity::class.java))
    }

    fun exit(view: View)
    {
        exitProcess(0)
    }
}