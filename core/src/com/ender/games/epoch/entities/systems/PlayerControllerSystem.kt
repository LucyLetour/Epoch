package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.PhysicsComponent
import com.ender.games.epoch.entities.components.PlayerComponent
import com.ender.games.epoch.entities.components.physics
import com.ender.games.epoch.entities.components.player
import com.ender.games.epoch.entities.createBullet
import kotlin.math.*

class PlayerControllerSystem:
        IteratingSystem(Family.all(PlayerComponent::class.java, PhysicsComponent::class.java).get()) {

    private val maxOmega = -3f..3f
    private val maxVel = -30f..30f

    private val targetAcceleration = 7f // m/s^2
    private val targetRotAccel = 2f // theta/s^2

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val oldVel = player.get(entity).smoothCamSubject.velocity.cpy()

        val body = physics.get(entity).body!!
        val rot = body.angle

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyForceToCenter(cos(rot) * (body.mass * targetAcceleration), sin(rot) * (body.mass * targetAcceleration), true) // F = ma
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyForceToCenter(-cos(rot) * (body.mass * targetAcceleration), -sin(rot) * (body.mass * targetAcceleration), true) // F = ma
        }

        // Move the ship around a circle of radius max_vel
        if(body.linearVelocity.len() !in maxVel) {
            val max = body.linearVelocity.cpy().nor().scl(30f)
            body.linearVelocity = max
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.angularVelocity in maxOmega) {
            body.applyTorque(-body.inertia * targetRotAccel, true) // tau = Ia
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.angularVelocity in maxOmega) {
            body.applyTorque(body.inertia * targetRotAccel, true) // tau = Ia
        }

        if ((!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) || body.angularVelocity !in maxOmega) {
            body.applyTorque(Interpolation.pow5Out.apply(0f, -body.angularVelocity * 500f, body.angularVelocity.absoluteValue / 5f), true)
        }

        if (Gdx.input.isTouched) {
            Player.ship.fire()
        }

        with(player.get(entity).smoothCamSubject) {
            accel = (body.linearVelocity.cpy().sub(oldVel)).scl(1f / deltaTime)
            //println("${body.linearVelocity}, $oldVel, ${1f/deltaTime}")
            velocity = body.linearVelocity
            pos = body.position
        }

        //player.get(entity).smoothCamSubject.aiming = Vector2(cos(rot), sin(rot))
    }
}