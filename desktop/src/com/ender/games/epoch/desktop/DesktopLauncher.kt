package com.ender.games.epoch.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.ender.games.epoch.Epochkt

fun main() {
    val config = LwjglApplicationConfiguration().apply {
        title = "Epoch"
        width = 1920
        height = 1080
        samples = 16
        vSyncEnabled = true
        foregroundFPS = 60
        useGL30 = true
        fullscreen = true
    }

    // Starts the game
    val app = LwjglApplication(
        Epochkt(
                screenHeight = config.height,
                screenWidth = config.width,
                debug = true
        ),
        config
    )
}