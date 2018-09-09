package com.epochgames.epoch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.epochgames.epoch.entities.components.TiledMapClickListener;
import com.epochgames.epoch.maps.EpochTiledMap;
import com.epochgames.epoch.maps.OpenSpaceMap;
import com.epochgames.epoch.screens.InGame;
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

    public InputMultiplexer inputMultiplexer;

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
        inputMultiplexer = new InputMultiplexer();
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
     * @param gameState the GameState to switch to
     */
    public void setGameState(GAME_STATE gameState) {
        this.gameState = gameState;
        game.setActiveScreen(gameState);
        setInputProcessorGM(gameState);
    }

    /**
     * Defines the inputMultiplexer based off the current gameState
     * @param gameState the current GameState
     */
    public void setInputProcessorGM(GAME_STATE gameState) {
        InputProcessor screenProcessor;
        Stage stageProcessor;

        switch (gameState) {
            case IN_GAME:
                screenProcessor = new InGameInputListener((InGame)game.activeScreen);
                stageProcessor = ((InGame)game.activeScreen).tileActorStage;
                break;
            default:
                Gdx.app.debug("Invalid Game State", "Game State was invalid, " +
                        "meaning input processors could not be assigned!");
                screenProcessor = null;
                stageProcessor = null;
                break;
        }

        inputMultiplexer.addProcessor(screenProcessor);
        inputMultiplexer.addProcessor(stageProcessor);

        Gdx.input.setInputProcessor(inputMultiplexer);
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
            //TODO L O G I C
        }
        return playerTurn;
    }
}
