package com.ender.games.epoch.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import com.ender.games.epoch.Ships
import com.ender.games.epoch.TAU
import com.ender.games.epoch.entities.systems.PIXELS_PER_METER
import com.ender.games.epoch.ship.*
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.streams.toList

const val KERNEL_RADIUS = .2f

fun rotationMatrix(theta: Float) = Matrix3(floatArrayOf( cos(theta), -sin(theta), 0f,
                                                         sin(theta),  cos(theta), 0f,
                                                         0f        ,  0f        , 1f))

fun main() {
    val s = Scanner(System.`in`)
    val indivCoords = s.tokens().toList()
    indivCoords.forEach { coord ->
        coord.forEach { print(it.toInt().toChar()) }
    }
    s.close()
}

fun getShipFixtureDefs(ship: Ships): List<Pair<FixtureDef, ShipPart>> {
    val lines = Gdx.files.internal("ships/shipLayouts/${ship.atlasRegion}.esl").reader().readLines()

    if(lines.first() != "kernel") {
        throw InvalidShipFixtureMap()
    }

    return lines.map { string: String ->
        if(string.startsWith("kernel")) {
            return@map Pair(
                first = FixtureDef().apply {
                    shape = CircleShape().apply {
                        radius = KERNEL_RADIUS
                        position = Vector2(0f, 0f)
                    }
                },
                second = ShipKernel()
            )
        } else {
            val type = string.split(" ").first()
            val bound = string.split(" ").last()

            return@map Pair(
                first = FixtureDef().apply {
                    shape = PolygonShape().apply {
                        set(bound.chunked(2).map { coord ->
                            Vector2(
                                    (coord.first().toInt().toFloat() - 64f) / PIXELS_PER_METER,
                                    (coord.last().toInt().toFloat() - 64f) / PIXELS_PER_METER
                            ).mul(rotationMatrix(3 * TAU / 4))
                        }.toTypedArray())
                    }
                },
                second = when(type) {
                    "WING" -> Wing()
                    "HARDPOINT" -> Hardpoint()
                    "THRUSTER" -> Thruster()
                    "BODY" -> Body()
                    else -> throw InvalidShipFixtureMap()
                }
            )
        }
    }.toList()
}