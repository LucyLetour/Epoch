package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.ender.games.epoch.ship.Ship

enum class Tactic {
    CHASE,
    SHOOT,
}

class EnemyComponent : Component {
    var active = true
    var ship: Ship? = null
    var tactic = Tactic.SHOOT
    var targetDist = 3f
    var shootRange = -0.1f..0.1f
}