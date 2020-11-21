package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.PhysicsComponent
import com.ender.games.epoch.entities.components.physics
import com.ender.games.epoch.screens.InGameScreen
import kotlin.math.min

const val MAX_STEP_TIME = 1/60f

class PhysicsSystem(private val world: World, private val game: InGameScreen):
        IteratingSystem(Family.all(PhysicsComponent::class.java).get()) {

    private var accumulator = 0f

    private val destroyList = mutableListOf<Entity>()
    private val addList = mutableListOf<Entity>()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if(physics.get(entity).toAdd) {
            addList.add(entity)
            physics.get(entity).toAdd = false
        }
        if(physics.get(entity).toDestroy) {
            destroyList.add(entity)
        }
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

        destroyList.forEach {
            world.destroyBody(physics.get(it).body)
            game.engine.removeEntity(it)
            destroyList.remove(it)
        }
    }

    companion object {
        var isUpdating = false
    }
}