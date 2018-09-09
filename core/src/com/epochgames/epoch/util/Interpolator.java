package com.epochgames.epoch.util;

import com.badlogic.gdx.math.Interpolation;

/**
 * Wrapper class for Interpolation
 */
public class Interpolator {
    public Interpolation interpolation;
    public float timeElapsed;
    public float progress;
    public float duration;
    public float startVal, endVal;

    public Interpolator(Interpolation interpolation) {
        this.interpolation = interpolation;
        timeElapsed = 0.0f;
    }

    public float interpolate(float delta) {
        timeElapsed += delta;
        progress = Math.min(1.0f, timeElapsed / duration);
        return interpolation.apply(startVal, endVal, progress);
    }

    public void setValues(float startVal, float endVal) {
        this.startVal = startVal;
        this.endVal = endVal;
        duration += 0.20f;
    }
}
