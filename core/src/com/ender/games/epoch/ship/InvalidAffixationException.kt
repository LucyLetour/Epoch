package com.ender.games.epoch.ship

import java.lang.Exception

class InvalidAffixationException(val reason: String):
        Exception("Affixation cannot be affixed. Reason: $reason")
