package com.epochgames.epoch.entities.components;

import com.badlogic.ashley.core.Component;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.hexworks.mixite.core.api.Hexagon;

public class StorageComponent implements Component {
    public Hexagon<HexSatelliteData> currentHexagon = null;
}
