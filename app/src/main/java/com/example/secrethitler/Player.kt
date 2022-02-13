package com.example.secrethitler

import java.io.Serializable
import java.lang.Exception


class Player(val name: String, private val role: Role) : Serializable {
    private var isAlive = true

    var lastPost = Post.CITIZEN

    var post = Post.CITIZEN
        set(value) {
            lastPost = post
            field = value
        }

    fun getRole(): Role {
        return role
    }

    fun getParty(): Role {
        if (role == Role.HITLER)
            return Role.FASCIST

        return role
    }

    fun die() {
        if (!isAlive)
            throw Exception("This player already dead")

        isAlive = false
    }
}