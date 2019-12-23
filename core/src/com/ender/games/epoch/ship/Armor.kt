package com.ender.games.epoch.ship

abstract class Armor(val value: Float, level: Int, parent: Ship):
        Affixation(parent, level)