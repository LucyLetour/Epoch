package com.ender.games.epoch.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.ender.games.epoch.Epochkt
import com.ender.games.epoch.util.BeatManager

class MainMenuScreen(val game: Epochkt) : ScreenAdapter() {

    val ui = Ui()
    private val beatManager = BeatManager

    private val guiBatch = SpriteBatch()
    private val font = BitmapFont()

    override fun render(delta: Float) {
        super.render(delta)

        //GL Stuff
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //Update and set projection matrix
        beatManager.tick(delta)

        guiBatch.begin()
        run {
            if (true) {
                //draw FPS counter
                font.color = Color.GREEN
                font.draw(guiBatch, "FPS: " + Gdx.graphics.framesPerSecond, 50f, (game.viewport.screenHeight - 50).toFloat())
                font.draw(guiBatch, "Measure: " + BeatManager.measure, 50f, (game.viewport.screenHeight - 100).toFloat())
                font.draw(guiBatch, "Beat: " + BeatManager.beat, 50f, (game.viewport.screenHeight - 150).toFloat())
                font.color = Color.WHITE
            }
        }
        ui.act(Gdx.graphics.deltaTime)
        ui.draw()
        guiBatch.end()

        game.camera.update()
    }
}