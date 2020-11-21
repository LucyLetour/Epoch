package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture

enum class RenderTypes { PLAYER, WALL, ENEMY, FORCE_FIELD }

class RenderComponent: Component {
    var representativeFixture: Fixture? = null
    var region: TextureRegion? = null
    var scale: Vector2 = Vector2(1f, 1f)
    var alpha = 0.0f
    var z = 0
    var rType: RenderTypes = RenderTypes.WALL
}