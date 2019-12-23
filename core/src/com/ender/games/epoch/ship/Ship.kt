package com.ender.games.epoch.ship

import com.badlogic.ashley.core.Entity
import com.ender.games.epoch.Ships
import com.ender.games.epoch.ship.weapons.LightBlaster
import com.ender.games.epoch.ship.weapons.Weapon

class Ship(baseStats: Ships, val entity: Entity) {
    val ar = baseStats.atlasRegion
    val name = baseStats.shipName
    val company = baseStats.company
    val type = baseStats.type
    var health = baseStats.health
    var armor = object: Armor(0f, 0, this) {}
    var cargoSpace = baseStats.cargoSpace
    var speed = baseStats.speed

    val weaponSlots = 2
    val weaponArray = MutableList<Weapon?>(weaponSlots){ null }

    val maxAffixLvl = 3

    fun affixArmor(armorPiece: Armor) {
        if(armorPiece.level <= maxAffixLvl) {
            armor = armorPiece
        } else {
            throw InvalidAffixationException("Armor level too high")
        }
    }

    fun affixWeapon(weapon: Weapon, slot: Int) {
        if(weapon.level <= maxAffixLvl) {
            if (weaponArray[slot] == null) {
                weaponArray[slot] = weapon
            }
        } else {
            throw InvalidAffixationException("Weapon level too high")
        }
    }

    fun fire() {
        for(weapon in weaponArray.filterNotNull()) {
            weapon.fireIfAble()
        }
    }
}