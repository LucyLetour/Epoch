package com.ender.games.epoch.smoothCamera

import com.badlogic.gdx.math.Vector2

class SmoothCamSubject(
        velocity: Vector2 = Vector2(0f, 0f),
        aiming: Vector2 = Vector2(0f, 0f)
): SmoothCamPoint() {
    var velocity = velocity.nor()
    var accel = Vector2.Zero
    var aiming = aiming.nor()
}