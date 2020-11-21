package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.Ships
import com.ender.games.epoch.entities.components.*
import com.ender.games.epoch.ship.Ship
import com.ender.games.epoch.ship.ShipKernel
import com.ender.games.epoch.ship.weapons.LightBlaster
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.InvalidShipException
import com.ender.games.epoch.util.Spritesheets
import com.ender.games.epoch.util.getShipFixtureDefs
import org.lwjgl.util.vector.Matrix2f

fun createShip(baseShip: Ships, pos: Vector2 = Vector2.Zero): Ship {
    return Ship(
        entity = Entity().apply entity@{
            val b = GAME_MANAGER.game!!.inGameScreen.world.createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                position.set(pos)
                angularDamping = 0.95f
                linearDamping = 0.3f
            }).apply {
                generateShipFixtures(baseShip, this)
                //userData = this@entity
            }

            add(PhysicsComponent().apply {
                body = b
            })
            add(RenderComponent().apply {
                alpha = 1f
                representativeFixture = b.fixtureList.first { it.userData is ShipKernel }
                region = ASSET_MANAGER.get(Spritesheets.SHIPS).findRegion(baseShip.atlasRegion)
                z = 0
            })
            add(EnemyComponent())
            add(HealthComponent())
            add(ShipComponent())
        },
        baseStats = baseShip
    ).apply {
        affixWeapon(LightBlaster(this), 0)
        enemy.get(entity).ship = this
        physics.get(entity).body!!.userData = this.entity
        ship.get(entity).ship = this
    }
}

fun removeShip(toRemove: Ship) {
    GAME_MANAGER.game!!.inGameScreen.world.destroyBody(physics.get(toRemove.entity).body)
    GAME_MANAGER.game!!.inGameScreen.engine.removeEntity(toRemove.entity)
}

fun generateShipFixtures(baseShip: Ships, body: Body) {
    body.apply {
        val defs = getShipFixtureDefs(baseShip)
        defs.forEach { (fixtureDef, shipPart) ->
            createFixture(fixtureDef).apply {
                userData = shipPart
                filterData.categoryBits = 2
            }
        }
    }.fixtureList.toList()
}


