package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.TAU
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.*
import kotlin.math.*

class EnemySystem :
        IteratingSystem(Family.all(EnemyComponent::class.java, PhysicsComponent::class.java).get()) {

    val player by lazy {
        physics.get(Player).body!!
    }

    val igs by lazy {
        GAME_MANAGER.game!!.inGameScreen
    }

    private val targetAcceleration = 50f // m/s^2
    private val targetRotAccel = 7f // theta/s^2 - In radians I think

    private fun minPS(v1: Float, v2: Float) = if(v1.absoluteValue < v2.absoluteValue) v1 else v2

    fun getAngForce(x: Float): Float {
        return if(x < 1.6) x else logisticCurve(x)
    }

    fun logisticCurve(x: Float): Float {
        val l = targetRotAccel // max val of func
        val k = 3.4f // steepness of curve
        val x0 = 1.6 // inflection point of curve
        // First term is logistic curve, second term (after -) is the value at x = 0 to make sure that curve(x) = 0
        return ((l / (1 + Math.E.pow(-k * (x  - x0)))) - (l / (1 + Math.E.pow(-k * (-x0))))).toFloat().coerceAtLeast(0f)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        if(this.entities.none { enemy.get(it).active }) {

            igs.clearRoom(igs.curRoom)
        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val eC = enemy.get(entity)
        val pC = physics.get(entity).body!!
        val sC = ship.get(entity)

        //val playerPos = player()
        if(eC.active) {
            when (eC.tactic) {
                Tactic.SHOOT -> {
                    // Angle in -180..180 => (360 + angle) % 360
                    // Angle in -pi..pi => Angle in 0..tau --> (tau + angle) % tau
                    // Apply force to look at player

                    val enemToShip = player.position.cpy().sub(pC.position)
                    val eAng = (pC.angle + TAU) % TAU
                    val theta = -minPS(
                            TAU - enemToShip.angleRad(Vector2(cos(eAng), sin(eAng))),
                            enemToShip.angleRad(Vector2(cos(eAng), sin(eAng)))
                    )

                    //pC.applyTorque((theta ).pow(9).coerceIn(-10f..10f), true)
                    pC.applyAngularImpulse(getAngForce(theta.absoluteValue) * sign(theta), true)

                    // Apply force to move to a set distance from player
                    val dist = pC.position.dst(player.position).absoluteValue
                    val force = (((cos(theta) + 0.3f) * (dist - eC.targetDist))).coerceIn(-50f..50f) * targetAcceleration
                    pC.applyForceToCenter(cos(pC.angle) * force, sin(pC.angle) * force, true)

                    // if within a certain theta from player, shoot
                    if (theta in eC.shootRange) {
                        sC.ship?.fire()
                    }
                }
                Tactic.CHASE -> {
                    // Apply force to look at player
                    // Apply force to move to a set distance from player
                }
            }
        }
    }

}