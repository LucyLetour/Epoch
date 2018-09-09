package com.epochgames.epoch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.epochgames.epoch.maps.EpochTiledMap;
import com.epochgames.epoch.maps.OpenSpaceMap;
import com.epochgames.epoch.screens.InputListeners.InGameInputListener;
import com.epochgames.epoch.util.EngineGameDesyncException;

public class GameManager {
    private static GameManager instance = new GameManager();

    //Controls speed of zoom. Lower is slower
    public static final float ZOOM_FACTOR = 0.2f;
    public static final float ZOOM_SPEED = 0.2f;
    public static final float START_ZOOM = 2.0f;

    private GAME_STATE gameState;
    public LOCATION location;
    public EpochTiledMap currentMap;
    public Program game;

    public boolean turnChanged;
    public boolean playerTurn;
    public long turnNumber;

    public InputProcessor inputProcessor;

    public enum GAME_STATE {
        MAIN_MENU,
        PAUSE_MENU,
        IN_GAME,
        OPTIONS_MENU,
        CREDITS
    }

    //Will need to be expanded to include every planet variation
    //Of planetary orbit and on planet
    public enum LOCATION {
        OPEN_SPACE,
        PLANETARY_ORBIT,
        ON_PLANET
    }

    public static GameManager getInstance() {
        return instance;
    }

    private GameManager() {
        gameState = GAME_STATE.IN_GAME;
        location = LOCATION.OPEN_SPACE;
        currentMap = new OpenSpaceMap();
    }

    public void setGame(Program game) {
        this.game = game;
    }

    public GAME_STATE getGameState() {
        return gameState;
    }

    public LOCATION getLocation() {
        return location;
    }

    /**
     * Sets the game state in the GameManager, updates the game,
     * and sets the input processor to whatever it should be
     * so that it switches the active screen
     * @param gameState
     */
    public void setGameState(GAME_STATE gameState) {
        this.gameState = gameState;
        setInputProcessorGM(gameState);
        game.setActiveScreen(gameState);
    }

    public void setInputProcessorGM(GAME_STATE gameState) {
        switch (gameState) {
            case IN_GAME:
                inputProcessor = new InGameInputListener(game.inGameScreen);
                break;
            default:
                break;
        }
        Gdx.input.setInputProcessor(inputProcessor);
    }

    /**
     * Goes to the next turn
     * Handles AI turns if not the player's turn
     * increments turn number
     * @return Whether or not it is the Player's turn
     */
    public boolean nextTurn() {
        playerTurn = !playerTurn;
        turnNumber++;
        if(!playerTurn) {
            try {
                //TODO Do I want this here? Change maybe (For clarity if nothing else)
                //Engine.instance.updateEngine(turnNumber);
                //TODO Temp
                throw new EngineGameDesyncException(0L, 0L);
            } catch (EngineGameDesyncException e) {
                e.printStackTrace();
            }
        }
        return playerTurn;
    }
}
