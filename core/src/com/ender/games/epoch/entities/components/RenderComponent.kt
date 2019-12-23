package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class RenderComponent : Component {
    var region: TextureRegion? = null
    var alpha = 0.0f
    var z = 0
}