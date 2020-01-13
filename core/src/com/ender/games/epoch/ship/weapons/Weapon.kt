package com.ender.games.epoch.ship.weapons

import com.ender.games.epoch.ship.Affixation
import com.ender.games.epoch.ship.Ship
import kotlin.reflect.KClass

abstract class Weapon(level: Int, val rof: Float, val rotationAngle: IntRange, parent: Ship, val munitionType: KClass<out Munition>, val reloadTime: Float):
        Affixation(parent, level) {

    var curMagazine: Munition? = null
        private set

    var weaponState: WeaponState = WeaponState.READY

    var startReload: Long = 0L

    fun load(magazine: Munition?) {
        if(munitionType.isInstance(magazine)) {
            startReload = System.currentTimeMillis()
            weaponState = WeaponState.LOADING
            curMagazine = magazine
        } else {
            if(magazine == null) {
                throw InvalidMunitionType(LightAmmo::class, munitionType)
            }
            throw InvalidMunitionType(magazine::class, munitionType)
        }
    }

    fun magEmpty() = (curMagazine?.count ?: 0) <= 0

    protected abstract fun fire()

    fun fireIfAble() {
        updateStates()
        if(!magEmpty() && weaponState == WeaponState.READY){
            curMagazine?.use()
            this.fire()
        }
    }

    var lastShot = System.currentTimeMillis()

    private fun updateStates() {
        weaponState =
                if(weaponState == WeaponState.LOADING) {
                    if((System.currentTimeMillis() - startReload) / 1000f > reloadTime) {
                        WeaponState.RECHARGING
                    } else {
                        WeaponState.LOADING
                    }
                } else if((System.currentTimeMillis() - lastShot) / 1000f > rof) {
                    lastShot = System.currentTimeMillis()
                    WeaponState.READY
                } else {
                    WeaponState.RECHARGING
                }
    }
}

enum class WeaponState {
    LOADING, READY, RECHARGING
}