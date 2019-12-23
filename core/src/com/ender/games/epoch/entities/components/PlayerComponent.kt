package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.ender.games.epoch.smoothCamera.SmoothCamSubject

class PlayerComponent: Component {
    val smoothCamSubject = SmoothCamSubject()
}