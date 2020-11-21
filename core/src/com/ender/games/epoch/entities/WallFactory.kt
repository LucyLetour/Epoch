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
            representativeFixture = f.apply {
                f.filterData.categoryBits = 1
            }
        })
    }
}

fun createForceField(f: Fixture): Entity {
    return Entity().apply {
        add(RenderLineComponent().apply {
            representativeFixture = f.apply {
                f.filterData.groupIndex = -1
            }
            rType = RenderTypes.FORCE_FIELD
        })
    }
}