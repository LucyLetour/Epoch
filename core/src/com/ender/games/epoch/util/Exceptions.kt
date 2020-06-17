package com.ender.games.epoch.util

import java.lang.Exception

class InvalidAffixationException(reason: String):
        Exception("Affixation cannot be affixed. Reason: $reason")

class InvalidShipException(info: String):
        Exception("Invalid Ship. $info.")

class InvalidShipFixtureMap(): Exception()