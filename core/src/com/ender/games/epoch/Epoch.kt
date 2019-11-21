package com.epochgames.epoch

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.epochgames.epoch.screens.InGame
import com.epochgames.epoch.screens.MainMenu
import org.hexworks.zircon.internal.util.Assets

class Epochkt(val screenWidth: Int,
              val screenHeight: Int,
              private val debug: Boolean = false): Game() {

    private var assetsLoaded = false

    val camera = OrthographicCamera()
    val viewport = ExtendViewport(1920f, 1080f, camera)

    val batch = SpriteBatch().apply { enableBlending() }
    val guiBatch = SpriteBatch()
    val font = BitmapFont()

    //val inGameScreen = InGame(this)
    //val mainMenuScreen = MainMenu(this)

    init {
        camera.setToOrtho(false, viewport.screenWidth.toFloat(), viewport.screenHeight.toFloat())
    }

    override fun create() {
        Gdx.app.logLevel = if(debug) Application.LOG_DEBUG else Application.LOG_INFO

        loadAssets()

        while(!assetsLoaded) {
            Gdx.app.log("Loading", "${Assets.MANAGER.progress * 100}% complete")

            assetsLoaded = Assets.MANAGER.update()
        }

        //gameManager.game = this
        gameManager.gameState = GameManager.GameState.IN_GAME
    }

    private fun loadAssets() {
        with(Assets.MANAGER) {
            load(com.epochgames.epoch.util.Assets.Spritesheets.SHIPS)
            load(com.epochgames.epoch.util.Assets.Spritesheets.PLANETS)
            load(com.epochgames.epoch.util.Assets.Textures.HEX_TILE)
        }
    }

    fun setActiveScreen(gameState: GameState) {
        when (gameState) {
            //GameManager.GameState.MAIN_MENU -> screen = mainMenuScreen
            //GameManager.GameState.IN_GAME -> screen = inGameScreen
            else -> {
                Gdx.app.error("Invalid Screen", "An invalid game state was given")
                screen = null
            }
        }
    }

    override fun resize(width: Int, height: Int) = viewport.update(width, height)

    companion object {
        val gameManager: GameManager = GameManager.getInstance()
    }
}