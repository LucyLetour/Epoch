package com.ender.games.epoch.screens.inputListeners

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.ender.games.epoch.entities.components.inputCode
import com.ender.games.epoch.entities.systems.*
import com.ender.games.epoch.screens.InGameScreen
import com.ender.games.epoch.util.BeatManager

class InGameInputListener(private val screen: InGameScreen): InputAdapter() {
    private val inputArray by lazy {
        inputCode.get(InputCode)
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        inputArray.m1 = true
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        when(character) {
            'z' -> BeatManager.triggerShop()
            'x' -> BeatManager.triggerLeaveShop()
            else -> return false
        }
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> inputArray.w = true
            Input.Keys.A -> inputArray.a = true
            Input.Keys.S -> inputArray.s = true
            Input.Keys.D -> inputArray.d = true
            else -> return false
        }
        return true
    }
    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> inputArray.w = false
            Input.Keys.A -> inputArray.a = false
            Input.Keys.S -> inputArray.s = false
            Input.Keys.D -> inputArray.d = false
            else -> return false
        }
        return true
    }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        inputArray.m1 = false
        return false
    }
}