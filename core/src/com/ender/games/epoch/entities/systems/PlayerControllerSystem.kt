package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Interpolation
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.*
import com.ender.games.epoch.util.BeatManager
import kotlin.math.*

class PlayerControllerSystem:
        IteratingSystem(Family.all(InputCodeComponent::class.java).get()) {

    private val maxOmega = -6f..6f
    private val maxVel = -30f..30f

    private val targetAcceleration = 7f // m/s^2
    private val targetRotAccel = 7f // theta/s^2 - In radians I think

    val body by lazy {
        physics.get(Player).body!!
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if(BeatManager.measure < 4) return

        val oldVel = player.get(Player).smoothCamSubject.velocity.cpy()
        val rot = body.angle

        if (inputCode.get(entity).z) {
            body.applyForceToCenter(cos(rot) * (body.mass * targetAcceleration), sin(rot) * (body.mass * targetAcceleration), true) // F = ma
        }

        if (inputCode.get(entity).w) {
            body.applyForceToCenter(cos(rot) * (body.mass * targetAcceleration), sin(rot) * (body.mass * targetAcceleration), true) // F = ma
        }

        if (inputCode.get(entity).s) {
            body.applyForceToCenter(-cos(rot) * (body.mass * targetAcceleration), -sin(rot) * (body.mass * targetAcceleration), true) // F = ma
        }

        // Move the ship around a circle of radius max_vel
        if(body.linearVelocity.len() !in maxVel) {
            val max = body.linearVelocity.cpy().nor().scl(30f) // TODO what is this 30
            body.linearVelocity = max
        }

        if (inputCode.get(entity).d && body.angularVelocity in maxOmega) {
            body.applyTorque(-body.inertia * targetRotAccel, true) // tau = Ia
        }

        if (inputCode.get(entity).a && body.angularVelocity in maxOmega) {
            body.applyTorque(body.inertia * targetRotAccel, true) // tau = Ia
        }

        if ((!inputCode.get(entity).d && !inputCode.get(entity).a) || body.angularVelocity !in maxOmega) {
            body.applyTorque(Interpolation.pow5Out.apply(0f, -body.angularVelocity * 200f, body.angularVelocity.absoluteValue / 5f), true)
        }

        if (inputCode.get(entity).m1) {
            Player.ship.fire()
        }

        with(player.get(Player).smoothCamSubject) {
            accel = (body.linearVelocity.cpy().sub(oldVel)).scl(1f / deltaTime)
            velocity = body.linearVelocity
            pos = body.position
        }
    }
}