package com.ender.games.epoch.ship

import com.badlogic.gdx.physics.box2d.Fixture
import com.ender.games.epoch.ship.weapons.Weapon

//TODO All of this, Sealed Class?

open class ShipPart

class Hardpoint(): ShipPart()//val level: Int, val fixture: Fixture, var weapon: Weapon? = null): ShipPart()

class Thruster(): ShipPart()

class ShipKernel(): ShipPart()

class Body(): ShipPart()

class Wing(): ShipPart()