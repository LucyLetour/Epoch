package com.ender.games.epoch.ship.weapons

import com.badlogic.ashley.core.Entity
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.components.BulletRequestComponent
import com.ender.games.epoch.ship.Ship

class LightBlaster(parent: Ship): Weapon(1, 0.1f, -45..45, parent, LightAmmo::class, 2f) {
    override fun fire() {
        GAME_MANAGER.game!!.inGameScreen.engine.addEntity(Entity().apply { add(BulletRequestComponent().apply { spawner = parent.entity }) })
    }
}