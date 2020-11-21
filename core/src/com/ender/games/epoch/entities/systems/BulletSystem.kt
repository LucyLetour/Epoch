package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.ender.games.epoch.BULLET_LIFETIME
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.entities.bulletList
import com.ender.games.epoch.entities.components.BulletRequestComponent
import com.ender.games.epoch.entities.components.bullet
import com.ender.games.epoch.entities.components.bulletReq
import com.ender.games.epoch.entities.createBullet
import com.ender.games.epoch.entities.removeBullet
import java.util.*

class BulletSystem: IteratingSystem(Family.all(BulletRequestComponent::class.java).get()) {

    private val bulletQueue = LinkedList<Entity>()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        bulletQueue.add(entity)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        if(!PhysicsSystem.isUpdating) {
            bulletList.filter {bullet.get(it) != null}.filter { bullet.get(it).delete || (System.currentTimeMillis() - bullet.get(it).aliveSince) > BULLET_LIFETIME}.forEach { removeBullet(it) }
            for(bullet in bulletQueue) {
                with(GAME_MANAGER.game!!.inGameScreen.engine) {
                    addEntity(createBullet(bulletReq.get(bullet).spawner!!))
                    removeEntity(bullet)
                }
            }

        }

        bulletQueue.clear()
    }
}