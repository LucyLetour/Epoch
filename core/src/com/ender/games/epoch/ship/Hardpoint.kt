package com.ender.games.epoch.ship

import com.badlogic.gdx.physics.box2d.Fixture
import com.ender.games.epoch.ship.weapons.Weapon

class Hardpoint(val level: Int, val fixture: Fixture, var weapon: Weapon? = null)