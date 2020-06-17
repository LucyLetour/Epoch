package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Fixture

class RenderComponent: Component {
    var representativeFixture: Fixture? = null
    var region: TextureRegion? = null
    var alpha = 0.0f
    var z = 0
}