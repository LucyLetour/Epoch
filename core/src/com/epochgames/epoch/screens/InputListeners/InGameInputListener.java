package com.epochgames.epoch.screens.InputListeners;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.screens.InGame;
import com.epochgames.epoch.util.EpochMath;
import com.epochgames.epoch.util.Interpolator;

/**
 * This class is more important than it seems at first glance because
 * it is responsible for hadling turns essentially. It must decide what's turn appropriate
 */
public class InGameInputListener implements InputProcessor {

    public InGame screen;

    public Vector2 touchDown = new Vector2();

    public InGameInputListener(InGame screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDown = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 newTouch = new Vector2(screenX, screenY);
        Vector2 delta = newTouch.cpy().sub(touchDown);
        screen.game.camera.translate(-delta.x, delta.y);
        touchDown = newTouch;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        screen.zoom(amount * GameManager.ZOOM_FACTOR);
        return false;
    }
}