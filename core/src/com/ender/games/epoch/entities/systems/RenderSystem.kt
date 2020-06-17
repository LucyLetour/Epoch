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
import com.ender.games.epoch.util.FixtureTexData

const val PIXELS_PER_METER = 40f

class RenderSystem(private val batch: SpriteBatch):
        SortedIteratingSystem(
                Family.all(RenderComponent::class.java)
                        .one(PhysicsComponent::class.java, WeaponComponent::class.java).get(),
                { o1: Entity, o2: Entity -> render.get(o2).z - render.get(o1).z})  {


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val rC = render.get(entity)

        if(rC.region == null) {
            Gdx.app.debug("Entity without an Icon", "An Entity was attempted to be rendered, but had no Icon to render!")
            return
        }

        with(Sprite(rC.region)) {
            setOriginBasedPosition(rC.representativeFixture?.body!!.position.x, rC.representativeFixture?.body!!.position.y)
            rotation = rC.representativeFixture?.body!!.angle// + (rC.representativeFixture?.userData as FixtureTexData).rot) * MathUtils.radiansToDegrees
            setScale(1 / PIXELS_PER_METER * 6, 1 / PIXELS_PER_METER * 6) // TODO wtf is that 6??
            color = Color( // Multiplying colors by the alpha gives a cool grey-out effect as alpha increases
                    this.color.r * rC.alpha,
                    this.color.g * rC.alpha,
                    this.color.b * rC.alpha,
                    this.color.a)
            draw(batch)
        }
    }
}