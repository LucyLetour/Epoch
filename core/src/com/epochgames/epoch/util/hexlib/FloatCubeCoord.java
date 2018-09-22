package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.Gdx;

public class FloatCubeCoord extends CubeCoord {
    public float x, y, z;

    public FloatCubeCoord(float x, float y, float z) {
        super((int)x, (int)y, (int)z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object cubeCoord) {
        if(cubeCoord instanceof CubeCoord) {
            return ((this.x == ((CubeCoord)cubeCoord).x) && (this.y == ((CubeCoord)cubeCoord).y) && (this.z == ((CubeCoord)cubeCoord).z));
        }
        else {
            Gdx.app.error("Incompatible Type", "Tried to compare an Cube Coordinate and a " + cubeCoord.getClass());
        }
        return false;
    }

    @Override
    public String toString() {
        return (x + ", " + y + ", " + z);
    }

    @Override
    public int hashCode() {
        return (int)((31 * x + 12) + (25 * y + 45) + (13 * z + 89));
    }
}
