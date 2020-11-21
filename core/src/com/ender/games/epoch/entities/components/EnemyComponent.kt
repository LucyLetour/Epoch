package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component

enum class Tactic {
    CHASE,
    SHOOT,
}

class EnemyComponent : Component {
    var active = false
    var tactic = Tactic.SHOOT
}