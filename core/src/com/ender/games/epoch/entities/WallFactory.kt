package com.ender.games.epoch.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.ender.games.epoch.GAME_MANAGER
import com.ender.games.epoch.HexRoomConstants
import com.ender.games.epoch.entities.components.RenderComponent
import com.ender.games.epoch.entities.components.RenderLineComponent
import com.ender.games.epoch.entities.components.RenderTypes
import com.ender.games.epoch.entities.systems.PIXELS_PER_METER
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.Textures

const val WALL_UNIT_LENGTH = 16f
const val HEX_UNIT_SIZE = 175f
const val CIRCLE_UNIT_SIZE = 298f

fun createWall(f: Fixture): Entity {
    return Entity().apply {
        add(RenderLineComponent().apply {
            representativeFixture = f
        })
        //val region: TextureRegion
        //val scale: Vector2

        /*when(f.shape) {
            is PolygonShape -> {
                /*when((f.shape as PolygonShape).vertexCount) {
                    6 -> {
                        add(RenderComponent().apply {
                            representativeFixture = f
                            region = TextureRegion(ASSET_MANAGER.get(Textures.HEX_UNIT))
                            rType = RenderTypes.WALL
                            scale = Vector2(1f, 1f).scl(1f)//HEX_UNIT_SIZE)
                        })
                        println("Hex!")
                    }
                    else -> {
                        /*var v1: Vector2 = Vector2.Zero
                        (f.shape as PolygonShape).getVertex(0, v1)
                        var v2: Vector2 = Vector2.Zero
                        (f.shape as PolygonShape).getVertex(1, v2)
                        add(RenderComponent().apply {
                            representativeFixture = f
                            region = TextureRegion(ASSET_MANAGER.get(Textures.WALL_UNIT))
                            rType = RenderTypes.WALL
                            scale = Vector2((v2.x - v2.y) * WALL_UNIT_LENGTH, 1f)
                        })*/
                    }
                }*/
            }
            else -> {
                /*add(RenderComponent().apply {
                    representativeFixture = f
                    region = TextureRegion(ASSET_MANAGER.get(Textures.CIRCLE_UNIT))
                    rType = RenderTypes.WALL
                    scale = Vector2(1f, 1f).scl(CIRCLE_UNIT_SIZE * f.shape.radius)
                })*/
            }
        }*/
    }
}