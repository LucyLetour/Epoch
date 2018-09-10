package com.epochgames.epoch.util.hexlib;

import com.badlogic.gdx.Gdx;

public class OffsetCoord {
    public int x, y;

    public OffsetCoord(int row, int col) {
        this.x = col;
        this.y = row;
    }

    @Override
    public boolean equals(Object offsetCoord) {
        if(offsetCoord instanceof OffsetCoord) {
            return ((this.x == ((OffsetCoord)offsetCoord).x) && (this.y == ((OffsetCoord)offsetCoord).y));
        }
        else {
            Gdx.app.error("Incompatible Type", "Tried to compare an Offset Coordinate and a " + offsetCoord.getClass());
        }
        return false;
    }

    @Override
    public String toString() {
        return (x + ", " + y);
    }

    @Override
    public int hashCode() {
        return (31 * x + 12) + (25 * y + 45);
    }
}
