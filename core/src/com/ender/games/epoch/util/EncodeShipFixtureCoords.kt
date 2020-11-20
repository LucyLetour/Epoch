package com.ender.games.epoch.util

import com.badlogic.gdx.Gdx
import com.ender.games.epoch.ship.*
import com.ender.games.epoch.Ships
import com.ender.games.epoch.entities.Player.ship
import java.io.File
import java.util.*

fun main() {
    val s = Scanner(System.`in`)
    val indivCoords = s.useDelimiter("").tokens()
    indivCoords.forEach {
        print("${it.toCharArray().first().toInt()} ")
    }
    s.close()
}