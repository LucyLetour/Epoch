package com.ender.games.epoch.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import com.ender.games.epoch.Ships
import java.util.*
import kotlin.streams.toList


data class ShipFixtures(val shape: Shape)

fun main() {
    val s = Scanner(System.`in`)
    val indivCoords = s.tokens().toList()
    indivCoords.forEach { coord ->
        coord.forEach { print(it.toInt().toChar()) }
    }
    s.close()
}

fun getShipFixtureDefs(ship: Ships): List<FixtureDef> {
    return decodeShipFixtureCoords(Gdx.files.internal("ships/shipLayouts/HEXACRON.esl").reader().readLines())
}

private fun decodeShipFixtureCoords(lines: List<String>): List<FixtureDef> {
    if(lines.first() != "kernel") {
        throw InvalidShipFixtureMap()
    }

    return lines.map { string: String ->
        FixtureDef().apply {
            shape =
                if(string == "kernel") {
                    CircleShape().apply {
                        radius = 0.2f
                        position = Vector2(0f, 0f)
                    }
                }
                else {
                    PolygonShape().apply {
                        println(string.chunked(2).size)
                                /*forEach {coord ->
                            println(Vector2(coord.first().toInt().toFloat(), coord.last().toInt().toFloat()))
                        }*/
                        set(string.chunked(2).map { coord ->
                            Vector2(coord.first().toInt().toFloat(), coord.last().toInt().toFloat())
                        }.toTypedArray())
                    }
                }
        }
    }.toList()
}