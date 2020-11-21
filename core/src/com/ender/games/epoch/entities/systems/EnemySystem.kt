package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.*

class EnemySystem :
        IteratingSystem(Family.all(EnemyComponent::class.java, PhysicsComponent::class.java).get()) {

    // Just for fun lol
    val player = physics.get(Player).body!!::getPosition

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val eC = enemy.get(entity)
        val pC = physics.get(entity)

        val playerPos = player()

        when(eC.tactic) {
            Tactic.SHOOT -> {
                // Apply force to look at player
                // Apply force to move to a set distance from player
                // if within a certain theta from player, shoot
            }
            Tactic.CHASE -> {
                // Apply force to look at player
                // Apply force to move to a set distance from player
            }
        }
    }

}