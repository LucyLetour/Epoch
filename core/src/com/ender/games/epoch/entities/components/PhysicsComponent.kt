package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Pool

class PhysicsComponent: Component, Pool.Poolable {
    var body: Body? = null
    var toDestroy = false
    var toAdd = true

    override fun reset() {
        body = null
    }
}