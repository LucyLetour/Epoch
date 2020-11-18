package com.ender.games.epoch.util

import com.badlogic.gdx.math.Vector2

data class MutablePair<a, b>(var first: a, var second: b)

fun Pair<Float, Float>.toVec2() = Vector2(first, second)