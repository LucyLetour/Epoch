package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.ender.games.epoch.entities.components.*

const val PIXELS_PER_METER = 40f

class RenderSystem(private val batch: SpriteBatch):
        SortedIteratingSystem(
                Family.all(RenderComponent::class.java, PhysicsComponent::class.java).get(), { o1: Entity, o2: Entity ->
                    o2.getComponent(RenderComponent::class.java).z - o1.getComponent(RenderComponent::class.java).z})  {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val rC = render.get(entity)
        val pC = physics.get(entity)

        if(rC.region == null) {
            Gdx.app.debug("Entity without an Icon", "An Entity was attempted to be rendered, but had no Icon to render!")
            return
        }

        with(Sprite(rC.region)) {
            setOriginBasedPosition(pC.body!!.position.x, pC.body!!.position.y)
            rotation = pC.body!!.angle * MathUtils.radiansToDegrees
            setScale(1 / PIXELS_PER_METER * 6, 1 / PIXELS_PER_METER * 6)
            color = Color(
                    this.color.r * rC.alpha,
                    this.color.g * rC.alpha,
                    this.color.b * rC.alpha,
                    this.color.a)
            draw(batch)
        }
    }
}