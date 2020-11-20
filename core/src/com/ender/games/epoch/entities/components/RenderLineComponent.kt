package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture

class RenderLineComponent: Component {
    var representativeFixture: Fixture? = null
    var alpha = 0.0f
    var z = 0
    var rType: RenderTypes = RenderTypes.WALL
    var coords = listOf<Vector2>()
}