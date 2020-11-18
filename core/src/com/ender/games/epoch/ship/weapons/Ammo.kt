package com.ender.games.epoch.ship.weapons

import com.badlogic.gdx.graphics.Texture
import com.ender.games.epoch.items.InventoryItem
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.Textures

sealed class Munition( var count: Int): InventoryItem {
    fun use() {
        count--
    }
}
class LightAmmo: Munition(60) {
    override fun getIcon() = ASSET_MANAGER.get(Textures.LIGHT_AMMO_TEX)
}

class HeavyAmmo: Munition(120) {
    override fun getIcon() = ASSET_MANAGER.get(Textures.MED_AMMO_TEX)
}
class LaserPulse: Munition(100) {
    override fun getIcon() = ASSET_MANAGER.get(Textures.HEAVY_AMMO_TEX)
}
