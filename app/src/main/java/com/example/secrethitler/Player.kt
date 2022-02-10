package com.example.secrethitler

import java.lang.Exception


class Player(val name: String, private val role: Role) {
    private var isAlive = true

    var lastPost = Post.CITIZEN
        set(value) = throw Exception("Cannot edit field lastPost, use post instead")

    var post = Post.CITIZEN
        set(value) {
            lastPost = post
            field = value
        }

    fun getPlayerRole(): Role {
        return role
    }

    fun getPlayerParty(): Role {
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