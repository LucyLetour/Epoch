package com.ender.games.epoch.ship

import com.badlogic.ashley.core.Entity
import com.ender.games.epoch.Ships
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.ship.weapons.InvalidMunitionType
import com.ender.games.epoch.ship.weapons.Munition
import com.ender.games.epoch.ship.weapons.Weapon
import com.ender.games.epoch.util.MutablePair
import kotlin.reflect.KClass

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
    val weaponArray = MutableList<MutablePair<Weapon, KClass<out Munition>>?>(weaponSlots){ null }

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
                weaponArray[slot] = MutablePair(weapon, weapon.munitionType)
            }
        } else {
            throw InvalidAffixationException("Weapon level too high")
        }
    }

    fun fire() {
        for(weapon in weaponArray.filterNotNull()) {
            if(weapon.first.magEmpty()) {
                try {
                    if (entity is Player) {
                        weapon.first.load((Player.inventory.removeItem(weapon.second) as Munition?))
                    } else {
                        weapon.first.load(weapon.second.objectInstance as Munition)
                    }
                } catch (e: InvalidMunitionType) {
                    TODO("Communicate there is no available ammo")
                }
            } else {
                weapon.first.fireIfAble()
            }
        }
    }
}