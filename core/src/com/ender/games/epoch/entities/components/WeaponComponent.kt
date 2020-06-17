package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import com.ender.games.epoch.ship.weapons.Weapon

class WeaponComponent: Pool.Poolable, Component {
    var weapon: Weapon? = null
    var shouldFire: Boolean = false
    var parent: Entity? = null

    override fun reset() {
        weapon = null
        shouldFire = false
        parent = null
    }
}