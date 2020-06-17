package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.ender.games.epoch.entities.components.WeaponComponent
import com.ender.games.epoch.entities.components.weapon

class WeaponSystem:
        IteratingSystem(Family.all(WeaponComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val wC = weapon.get(entity)
        if(wC.shouldFire) {
            wC.weapon?.fireIfAble()
        }
    }
}