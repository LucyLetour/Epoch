package com.ender.games.epoch.entities.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.HexRoomConstants
import com.ender.games.epoch.TAU
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.*
import com.ender.games.epoch.util.BeatManager
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

const val PIXELS_PER_METER = 40f

class RenderSystem(private val batch: SpriteBatch, private val wallRenderer: ShapeRenderer):
        SortedIteratingSystem(
                Family.one(RenderComponent::class.java, RenderLineComponent::class.java).get(),
                { o1: Entity, o2: Entity -> if(render.has(o2)) render.get(o2).z else renderLine.get(o2).z - if(render.has(o1)) render.get(o1).z else renderLine.get(o1).z})  {

    val startTime by lazy{
        GAME_MANAGER.game!!.inGameScreen.startTime
    }

    private fun color(x: Long) = (2f * sin((x / 1000f) * TAU) + 0.5).toFloat().coerceIn(0f, 1f)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val timeSinceStart = System.currentTimeMillis() - BeatManager.start

        if(render.has(entity)) {
            batch.begin()
            val rC = render.get(entity)

            val c = when (rC.rType) {
                RenderTypes.PLAYER -> {
                    if (BeatManager.measure < 5) {
                        Color.BLACK.cpy().lerp(Color.PURPLE, ((BeatManager.measure - 1) * 4f + BeatManager.beat - 1) / 15f)
                    } else {
                        Color.PURPLE.cpy().lerp(Color.BLUE, color(timeSinceStart))
                    }
                }
                RenderTypes.WALL -> {
                    Color.GREEN
                }
                RenderTypes.ENEMY -> {
                    Color.RED
                }
                RenderTypes.FORCE_FIELD -> {
                    Color.RED
                }
            }

            if (rC.region == null) {
                Gdx.app.debug("Entity without an Icon", "An Entity was attempted to be rendered, but had no Icon to render!")
                return
            }

            with(Sprite(rC.region)) {
                color = c

                setOriginBasedPosition(rC.representativeFixture?.body!!.position.x, rC.representativeFixture?.body!!.position.y)
                rotation = -90 + rC.representativeFixture?.body!!.angle * MathUtils.radiansToDegrees// + (rC.representativeFixture?.userData as FixtureTexData).rot) * MathUtils.radiansToDegrees
                setScale(1 / PIXELS_PER_METER * rC.scale.x, 1 / PIXELS_PER_METER * rC.scale.y) // TODO wtf is that 6?? // Removed the *6 from each term...
                color = Color( // Multiplying colors by the alpha gives a cool grey-out effect as alpha increases
                        this.color.r * rC.alpha,
                        this.color.g * rC.alpha,
                        this.color.b * rC.alpha,
                        this.color.a)
                draw(batch)
            }
            batch.end()
        } else {
            wallRenderer.begin(ShapeRenderer.ShapeType.Line)
            wallRenderer.setAutoShapeType(true)
            val rLC = renderLine.get(entity)
            wallRenderer.color = if(rLC.rType == RenderTypes.WALL) Color.GREEN else Color.RED
            //wallRenderer.rotate(0f, 0f, 1f, Math.toDegrees(rLC.representativeFixture!!.body.angle.toDouble()).toFloat())
            when(rLC.representativeFixture!!.shape) {
                is PolygonShape -> {
                    val f = (rLC.representativeFixture!!.shape as PolygonShape)
                    val verts = List(f.vertexCount) { Vector2.Zero.cpy() }
                            .mapIndexed { idx, it -> f.getVertex(idx, it); it }
                            .map {it.rotate(Math.toDegrees(rLC.representativeFixture!!.body.angle.toDouble()).toFloat())}
                            .map { it.add(rLC.representativeFixture!!.body.position) }

                    verts.forEachIndexed { idx, i ->
                        wallRenderer.rectLine(i, verts[(idx + 1) % (verts.size)], HexRoomConstants.HEX_ROOM_WALL_THICKNESS)
                    }

                }
                is CircleShape -> {
                    val f = (rLC.representativeFixture!!.shape as CircleShape)
                    if(rLC.representativeFixture!!.userData == true) {
                        wallRenderer.arc(
                                f.position.x + rLC.representativeFixture!!.body.position.x,
                                f.position.y + rLC.representativeFixture!!.body.position.y,
                                f.radius,
                                120f + Math.toDegrees(atan2(f.position.y, f.position.x).toDouble()).toFloat(),
                                120f,
                                12
                        )
                    } else {
                        wallRenderer.circle(
                                f.position.x + rLC.representativeFixture!!.body.position.x,
                                f.position.y + rLC.representativeFixture!!.body.position.y,
                                f.radius
                        )
                    }
                }
            }
            wallRenderer.end()
        }
    }
}