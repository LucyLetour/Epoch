package com.ender.games.epoch

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.ender.games.epoch.screens.InGameScreen
import com.ender.games.epoch.util.ASSET_MANAGER
import com.ender.games.epoch.util.Spritesheets
import com.ender.games.epoch.util.UI

class Epochkt(val screenWidth: Int,
              val screenHeight: Int,
              val debug: Boolean = false): Game() {

    val camera = OrthographicCamera()
    val viewport = ExtendViewport(19.2f, 10.8f, camera)

    lateinit var inGameScreen: InGameScreen
    //private val mainMenuScreen = MainMenu(this)

    override fun create() {
        GAME_MANAGER.game = this

        Gdx.app.logLevel = if(debug) Application.LOG_DEBUG else Application.LOG_INFO

        loadAssets()

        while(!ASSET_MANAGER.update()) {
            //Gdx.app.log("Loading", "${ASSET_MANAGER.progress * 100}% complete")
        }

        inGameScreen = InGameScreen(this)
        GAME_MANAGER.setGameState(GameState.IN_GAME)

        camera.setToOrtho(false, viewport.screenWidth.toFloat(), viewport.screenHeight.toFloat())

    }

    private fun loadAssets() {
        with(ASSET_MANAGER) {
            load(Spritesheets.SHIPS)
            load(Spritesheets.PLANETS)
            load(UI.SKIN)
            load(UI.ATLAS)
        }
    }

    fun setActiveScreen(gameState: GameState) {
        when (gameState) {
            // GameState.MAIN_MENU -> screen = mainMenuScreen
            GameState.IN_GAME -> setScreen(inGameScreen)
            else -> {
                Gdx.app.error("Invalid Screen", "An invalid game state was given")
                screen = null
            }
        }
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)
}