package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

class BulletComponent: Component, Pool.Poolable {
    var aliveSince: Long = 0L
    var delete = false

    override fun reset() {
        aliveSince = 0L
        delete = false
    }
}