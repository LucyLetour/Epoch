package com.ender.games.epoch.smoothCamera

import com.badlogic.gdx.math.Vector2
import kotlin.math.max

const val MAX_FOLLOW_DIST = 2f

class SmoothCamWorld(val subject: SmoothCamSubject) {
    val points = mutableListOf<SmoothCamPoint>()
    val pos = Vector2(0f, 0f)
    var velX = 0f
    var velY = 0f
    var zoom = 0f
        private set

    fun getNearestPointOfInterest(p1: SmoothCamPoint): SmoothCamPoint? {
        return points.sortedBy { getDist(p1, it) }[0]
    }

    fun update(delta: Float) {
        val coeff: Float
        if(points.isNotEmpty()) {
            val nearestPoint = getNearestPointOfInterest(subject)
            val dist = getDist(subject, nearestPoint!!)
            if(dist > nearestPoint.outerRadius) {
                coeff = 1f
                pos.set(subject.pos)
                zoom = 1f
            } else {
                coeff = max((dist - nearestPoint.innerRadius) / nearestPoint.radiusDist(), 0f)
                val deltaX = subject.pos.x - nearestPoint.pos.x
                val deltaY = subject.pos.y - nearestPoint.pos.y
                if(nearestPoint.positivePolarity) {
                    pos.x = nearestPoint.pos.x + coeff * deltaX
                    pos.y = nearestPoint.pos.y + coeff * deltaY
                } else {
                    try {
                        pos.x = subject.pos.x + (deltaX / dist * (nearestPoint.outerRadius - dist))
                        pos.y = subject.pos.y + (deltaY / dist * (nearestPoint.outerRadius - dist))
                    } catch (e: ArithmeticException) {
                        pos.x = 0f
                        pos.y = 0f
                    }
                }
                zoom = 1f + nearestPoint.zoom * (1f - coeff)
            }
        } else {
            coeff = 1f
        }

        //pos.set(subject.pos)
        //println(subject.accel)
        //println(((pos.dst(subject.pos) / MAX_FOLLOW_DIST).absoluteValue.coerceIn(0f..1f) * 0.2f) + 0.8f)
        //println("$pos -> ${subject.pos} == ${pos.dst(subject.pos)}")

        //x += velX * coeff
        //y += velY * delta * coeff

        //x += subject.aiming.len() * subject.aiming.x * coeff
        //y += subject.aiming.len() * subject.aiming.y * coeff
    }

    companion object {
        fun getDist(p1: SmoothCamPoint, p2: SmoothCamPoint) = p1.pos.dst(p2.pos)
    }
}