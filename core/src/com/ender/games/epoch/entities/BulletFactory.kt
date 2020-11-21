package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.components.BulletComponent
import com.ender.games.epoch.entities.components.PhysicsComponent
import com.ender.games.epoch.entities.components.physics
import kotlin.math.cos
import kotlin.math.sin

val bulletList = mutableListOf<Entity>()

fun createBullet(spawner: Entity, offset: Vector2 = Vector2(4f,0f)): Entity {
    val entityPos = physics.get(spawner).body!!.position
    val entityRot = physics.get(spawner).body!!.angle
    return Entity().apply entity@{
        add(PhysicsComponent().apply {
            body = GAME_MANAGER.game!!.inGameScreen.world.createBody( BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                position.set(entityPos)
                bullet = true
            }).apply {
                createFixture(FixtureDef().apply {
                    shape = PolygonShape().apply {
                        setAsBox(.1f,
                                .01f,
                                offset.rotateRad(entityRot),
                                entityRot)
                    }
                    density = 1f
                })
                linearVelocity = Vector2(500f * cos(entityRot),  500f * sin(entityRot)).add(physics.get(spawner).body!!.linearVelocity)
                userData = this@entity
            }
        })
        add(BulletComponent().apply {
            aliveSince = System.currentTimeMillis()
        })
    }.also {
        if(bulletList.size > 1000) {
            removeBullet(bulletList[0])
        }
        bulletList.add(it)
    }
}

fun removeBullet(bullet: Entity) {
    with( GAME_MANAGER.game!!.inGameScreen) {
        bulletList.remove(bullet)
        removeBody(bullet)
    }
}