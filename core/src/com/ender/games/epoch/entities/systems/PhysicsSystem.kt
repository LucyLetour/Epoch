package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.ender.games.epoch.Epochkt
import com.ender.games.epoch.entities.components.TransformComponent

class MoveSystem(val game: Epochkt): IteratingSystem(Family.all(TransformComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {

    }
}