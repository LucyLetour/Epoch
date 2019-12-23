package com.ender.games.epoch.ship.weapons

import com.ender.games.epoch.ship.Affixation
import com.ender.games.epoch.ship.Ship

abstract class Weapon(level: Int, val rof: Float, val rotationAngle: IntRange, parent: Ship):
        Affixation(parent, level) {

    protected abstract fun fire()

    fun fireIfAble() {
        if(canFire()){
            this.fire()
        }
    }

    var lastShot = System.currentTimeMillis()

    private fun canFire(): Boolean {
        if((System.currentTimeMillis() - lastShot) / 1000f > rof) {
            lastShot = System.currentTimeMillis()
            return true
        }
        return false
    }
}