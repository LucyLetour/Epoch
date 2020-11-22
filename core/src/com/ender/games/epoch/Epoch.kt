package com.ender.games.epoch

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.ender.games.epoch.screens.InGameScreen
import com.ender.games.epoch.screens.MainMenuScreen
import com.ender.games.epoch.util.*
import kotlin.properties.Delegates

class Epochkt(val screenWidth: Int,
              val screenHeight: Int,
              val debug: Boolean = false): Game() {

    val camera = OrthographicCamera()
    val viewport = ExtendViewport(192f / 4, 108f / 4, camera)

    lateinit var inGameScreen: InGameScreen
    lateinit var mainMenuScreen: MainMenuScreen

    override fun create() {
        GAME_MANAGER.game = this

        Gdx.app.logLevel = if(debug) Application.LOG_DEBUG else Application.LOG_INFO

        loadAssets()

        while(!ASSET_MANAGER.update()) {
            //Gdx.app.log("Loading", "${ASSET_MANAGER.progress * 100}% complete")
        }

        inGameScreen = InGameScreen(this)
        mainMenuScreen = MainMenuScreen(this)
        GAME_MANAGER.setGameState(GameState.MAIN_MENU)

        camera.setToOrtho(false, viewport.screenWidth.toFloat(), viewport.screenHeight.toFloat())

    }

    private fun loadAssets() {
        with(ASSET_MANAGER) {
            load(Spritesheets.SHIPS)
            load(Spritesheets.PLANETS)
            load(UI.SKIN)
            load(UI.ATLAS)
            load(Textures.LIGHT_AMMO_TEX)
            load(Textures.MED_AMMO_TEX)
            load(Textures.HEAVY_AMMO_TEX)
            load(Textures.TEMP_HEXACRON_TEST)
            load(Audio.MUSIC)
            load(Textures.HEX_UNIT)
            load(Textures.WALL_UNIT)
            load(Textures.CIRCLE_UNIT)
        }
    }

    fun setActiveScreen(gameState: GameState) {
        when (gameState) {
            // GameState.MAIN_MENU -> screen = mainMenuScreen
            GameState.IN_GAME -> setScreen(inGameScreen)
            GameState.MAIN_MENU -> setScreen(mainMenuScreen)
            else -> {
                Gdx.app.error("Invalid Screen", "An invalid game state was given")
                screen = null
            }
        }
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)
}