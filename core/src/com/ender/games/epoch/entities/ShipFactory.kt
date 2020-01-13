package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.Ships
import com.ender.games.epoch.entities.components.PhysicsComponent
import com.ender.games.epoch.entities.components.RenderComponent
import com.ender.games.epoch.entities.components.physics
import com.ender.games.epoch.ship.Ship
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.Spritesheets

fun createShip(baseShip: Ships): Ship {
    return Ship(
            entity = Entity().apply {
                    add(RenderComponent().apply {
                        alpha = 1f
                        region = ASSET_MANAGER.get(Spritesheets.SHIPS).findRegion(baseShip.atlasRegion)
                        z = 0
                    })

                    add(PhysicsComponent().apply {
                        body = GAME_MANAGER.game!!.inGameScreen.world.createBody(BodyDef().apply {
                            type = BodyDef.BodyType.DynamicBody
                            position.set(6f, 6f)
                            angularDamping = 0.2f
                        }).apply {
                            createFixture(FixtureDef().apply {
                                // TODO Fixture based off of ship
                                shape = CircleShape().apply {
                                    radius = 3f
                                }
                                density = 4000f
                            })
                        }
                    })
                },
            baseStats = baseShip
    )
}

fun removeShip(toRemove: Ship) {
    GAME_MANAGER.game!!.inGameScreen.world.destroyBody(physics.get(toRemove.entity).body)
    GAME_MANAGER.game!!.inGameScreen.engine.removeEntity(toRemove.entity)
}