package com.ender.games.epoch.screens.inputListeners

/*import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.ender.games.epoch.screens.InGameScreen

class InGameInputListener(private val screen: InGameScreen): InputProcessor {
    private var touchdown = Vector2()

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //touchdown = Vector2(screenX.toFloat(), screenY.toFloat())
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        //val newTouch = Vector2(screenX.toFloat(), screenY.toFloat())
        //val delta = newTouch.cpy().sub(touchdown)
        //screen.scroll(-delta.x, delta.y)
        //touchdown = newTouch
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        //screen.zoom(amount * ZOOM_FACTOR * GAME_MANAGER.game!!.camera.zoom)
        return false
    }

    override fun keyDown(keycode: Int) = false
    override fun keyUp(keycode: Int) = false
    override fun keyTyped(character: Char) = false
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false
    override fun mouseMoved(screenX: Int, screenY: Int) = false
}*/