package com.ender.games.epoch.util

import com.badlogic.gdx.math.Vector2
import com.ender.games.epoch.Ships

fun genShipFixtureVertexLists(baseShip: Ships): List<List<Vector2>> {
    return when(baseShip) {
        Ships.HEXACRON -> {
            HEXACRON_LIST
        }
        else -> HEXACRON_LIST
    }
}

val HEXACRON_LIST = listOf(
        listOf(Vector2(64f, 15f), Vector2(51f, 56f), Vector2(54f, 80f), Vector2(56f, 86f), Vector2(72f, 86f), Vector2(74f, 80f), Vector2(77f, 56f))
)