package com.ender.games.epoch.smoothCamera

import com.badlogic.gdx.math.Vector2

open class SmoothCamPoint(
        var pos: Vector2 = Vector2(0f, 0f),
        var outerRadius: Float = 0f,
        var innerRadius: Float = 0f,
        var zoom: Float = 0f,
        var positivePolarity: Boolean = true
) {
    val radiusDist = fun():Float = outerRadius - innerRadius
}