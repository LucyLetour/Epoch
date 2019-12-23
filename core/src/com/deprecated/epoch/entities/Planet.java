package com.epochgames.epoch.entities;

import com.ender.games.epoch.GameManager;

public class Planet {
    private GameManager.Planets planet;

    public Planet(GameManager.Planets planet) {
        this.planet = planet;
    }

    public GameManager.Planets getPlanet() {
        return planet;
    }
}
