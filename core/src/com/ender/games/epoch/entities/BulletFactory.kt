package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.components.PhysicsComponent
import com.ender.games.epoch.entities.components.physics
import kotlin.math.cos
import kotlin.math.sin

val bulletList = mutableListOf<Entity>()

fun createBullet(spawner: Entity, offset: Vector2 = Vector2(0f,0f)): Entity {
    val entityPos = physics.get(spawner).body!!.position
    val entityRot = physics.get(spawner).body!!.angle
    return GAME_MANAGER.game!!.inGameScreen.engine.createEntity().apply {
        add(PhysicsComponent().apply {
            val bodyDef = BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                position.set(entityPos.add(offset))
                bullet = true
            }
            body = GAME_MANAGER.game!!.inGameScreen.world.createBody(bodyDef).apply {
                createFixture(FixtureDef().apply {
                    shape = PolygonShape().apply {
                        setAsBox(.1f,
                                .01f,
                                Vector2(0f, 0f),
                                entityRot)
                    }
                    density = 1f
                })
                linearVelocity = Vector2(500f * cos(entityRot),  500f * sin(entityRot)).add(physics.get(spawner).body!!.linearVelocity)
            }
        })
    }.also {
        if(bulletList.size > 1000) {
            with( GAME_MANAGER.game!!.inGameScreen) {
                world.destroyBody(physics.get(bulletList[0]).body)
                engine.removeEntity(bulletList.removeAt(0))
            }
        }
        bulletList.add(it)
    }
}