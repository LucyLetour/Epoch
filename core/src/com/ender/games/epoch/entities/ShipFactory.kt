package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.*
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.Ships
import com.ender.games.epoch.entities.components.PhysicsComponent
import com.ender.games.epoch.entities.components.RenderComponent
import com.ender.games.epoch.entities.components.physics
import com.ender.games.epoch.ship.Ship
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.InvalidShipException
import com.ender.games.epoch.util.Spritesheets
import com.ender.games.epoch.util.getShipFixtureDefs

fun createShip(baseShip: Ships): Ship {
    return Ship(
            entity = Entity().apply {
                    add(PhysicsComponent().apply {
                        body = GAME_MANAGER.game!!.inGameScreen.world.createBody(BodyDef().apply {
                            type = BodyDef.BodyType.DynamicBody
                            position.set(6f, 6f)
                            angularDamping = 0.2f
                        }).apply {
                            generateShipFixtures(baseShip, this)
                        }
                    })
                    add(RenderComponent().apply {
                        alpha = 1f
                        region = ASSET_MANAGER.get(Spritesheets.SHIPS).findRegion(baseShip.atlasRegion)
                        z = 0
                    })
                },
            baseStats = baseShip
    )
}

fun removeShip(toRemove: Ship) {
    GAME_MANAGER.game!!.inGameScreen.world.destroyBody(physics.get(toRemove.entity).body)
    GAME_MANAGER.game!!.inGameScreen.engine.removeEntity(toRemove.entity)
}

fun generateShipFixtures(baseShip: Ships, body: Body) {
    when(baseShip) {
        Ships.HEXACRON -> {
            body.apply {
                val defs = getShipFixtureDefs(baseShip)
                createFixture(defs.first()).apply {
                    userData = ShipKernel()
                }
                defs.drop(1).forEach {
                    this.createFixture(it)
                }
            }.fixtureList.toList()
        }
        else -> {
            throw InvalidShipException("${baseShip.shipName} does not have hardpoints to initialize")
        }
    }
}

class ShipKernel()