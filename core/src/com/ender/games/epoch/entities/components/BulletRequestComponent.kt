package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool

class BulletRequestComponent: Component, Pool.Poolable {

    var spawner: Entity? = null
    var offset = Vector2.Zero
    var randAngle = Vector2.Zero

    override fun reset() {
        spawner = null
        offset = Vector2.Zero
        randAngle = Vector2.Zero
    }
}