package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.*

class HealthSystem :
        IteratingSystem(Family.all(HealthComponent::class.java).get()) {

    val igs by lazy {
        GAME_MANAGER.game!!.inGameScreen
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val hC = health.get(entity)
        if(hC.health <= 0) {
            println(enemy.has(entity))
            if(entity is Player) {
                igs.gameOver()
            } else if(enemy.has(entity)) {
                enemy.get(entity).active = false
            }
        }
    }

}