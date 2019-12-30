package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.components.PhysicsComponent

class Asteroid(xPos: Float, yPos: Float): Entity() {
    private val world = GAME_MANAGER.game!!.inGameScreen.world

    init {
        add(PhysicsComponent().apply {
            body = world.createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                position.set(xPos, yPos)
            }).apply {
                createFixture(FixtureDef().apply {
                    shape = CircleShape().apply {
                        radius = 2f
                    }
                    density = 0.5f
                })
            }
        })
    }
}