package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.components.bullet
import com.ender.games.epoch.screens.InGameScreen
import com.ender.games.epoch.ship.ShipKernel
import com.ender.games.epoch.util.HexCoord

class B2CollisionListener(val game: InGameScreen): ContactListener {
    override fun beginContact(contact: Contact) {
        if(!contact.fixtureA.isSensor && !contact.fixtureB.isSensor) { //Bullets
            val eA = contact.fixtureA.body.userData as Entity?
            val eB = contact.fixtureB.body.userData as Entity?

            if (eA != null && bullet.has(eA)) {
                bullet.get(eA).delete = true
            }

            if (eB != null && bullet.has(eB)) {
                bullet.get(eB).delete = true
            }
        } else { // Room detection
            if(contact.fixtureA.isSensor) {
                if(contact.fixtureA.userData is HexCoord && contact.fixtureB.userData is ShipKernel) {
                    game.enterRoom(contact.fixtureA.userData as HexCoord)
                } else if (contact.fixtureA.userData is Pair<*, *>) {

                }
            } else if (contact.fixtureB.isSensor) {
                if(contact.fixtureB.userData is HexCoord && contact.fixtureA.userData is ShipKernel) {
                    game.enterRoom(contact.fixtureB.userData as HexCoord)
                } else if (contact.fixtureB.userData is Pair<*, *>) {

                }
            }
        }
    }

    override fun endContact(contact: Contact) {
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}