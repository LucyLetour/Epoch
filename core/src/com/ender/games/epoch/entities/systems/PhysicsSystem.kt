package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import com.ender.games.epoch.entities.components.PhysicsComponent
import kotlin.math.min

const val MAX_STEP_TIME = 1/60f

class PhysicsSystem(private val world: World):
        IteratingSystem(Family.all(PhysicsComponent::class.java).get()) {

    private var accumulator = 0f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        // Do nothing
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        val frameTime = min(deltaTime, 0.25f)
        accumulator += frameTime

        if(accumulator >= MAX_STEP_TIME) {
            isUpdating = true
            world.step(MAX_STEP_TIME, 6, 2)
            isUpdating = false
            accumulator -= MAX_STEP_TIME
        }
    }

    companion object {
        var isUpdating = false
    }
}