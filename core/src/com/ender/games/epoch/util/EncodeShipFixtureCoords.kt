package com.ender.games.epoch.util

import java.util.*

fun main() {
    val s = Scanner(System.`in`)
    val indivCoords = s.useDelimiter(" ").tokens().map { it.split(",") }
    indivCoords.forEach { coord ->
        coord.forEach { print(it.toInt().toChar()) }
    }
    s.close()
}