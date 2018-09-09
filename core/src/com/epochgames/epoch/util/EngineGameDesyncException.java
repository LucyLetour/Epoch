package com.epochgames.epoch.util;

public class EngineGameDesyncException extends Exception {
    public EngineGameDesyncException(long gameTurn, long engineTurn) {
        super("Game was on turn " + gameTurn + ", but Engine was on turn " + engineTurn + "!");
    }
}