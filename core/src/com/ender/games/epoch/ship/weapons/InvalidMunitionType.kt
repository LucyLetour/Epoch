package com.ender.games.epoch.ship.weapons

import kotlin.reflect.KClass

class InvalidMunitionType(actual: KClass<out Munition>, expected: KClass<out Munition>):
        Exception("Attempted to load $actual munitions, expected $expected")