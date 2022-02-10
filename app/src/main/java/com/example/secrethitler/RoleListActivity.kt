package com.example.secrethitler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import java.io.Serializable

class RoleListActivity : AppCompatActivity()
{
    private lateinit var game: Serializable

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_list)

        game = intent.getSerializableExtra("game")!!
    }
}