package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.ender.games.epoch.entities.components.bullet

class B2CollisionListener: ContactListener {
    override fun beginContact(contact: Contact) {
        val eA = contact.fixtureA.body.userData as Entity?
        val eB = contact.fixtureB.body.userData as Entity?
        if(eA != null && bullet.has(eA)) {
            bullet.get(eA).delete = true
        }
        if(eB != null && bullet.has(eB)) {
            bullet.get(eB).delete = true
        }
    }

    override fun endContact(contact: Contact) {

    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}