package com.ender.games.epoch.entities.components

import com.badlogic.ashley.core.Component
import com.ender.games.epoch.ship.Ship

class ShipComponent: Component {
    var ship: Ship? = null
}