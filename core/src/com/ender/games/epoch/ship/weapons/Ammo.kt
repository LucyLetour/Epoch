package com.ender.games.epoch.ship.weapons

import com.ender.games.epoch.items.InventoryItem

sealed class Munition( var count: Int): InventoryItem {
    fun use() {
        count--
    }
}
class LightAmmo: Munition(60)
class HeavyAmmo: Munition(120)
class LaserPulse: Munition(100)
