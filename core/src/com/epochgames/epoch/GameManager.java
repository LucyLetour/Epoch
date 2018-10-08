package com.epochgames.epoch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.epochgames.epoch.maps.EpochTiledMap;
import com.epochgames.epoch.maps.OpenSpaceMap;
import com.epochgames.epoch.screens.InGame;
import com.epochgames.epoch.screens.inputListeners.InGameInputListener;

public class GameManager {
    private static GameManager instance = new GameManager();

    //Controls speed of zoom. Lower is slower
    public static final float ZOOM_FACTOR = 0.2f;
    public static final float ZOOM_SPEED = 0.2f;
    public static final float START_ZOOM = 2.0f;
    public static final float MIN_ZOOM = 1.0f;
    public static final float MAX_ZOOM = 4.0f;

    //Controls speed of camera for moving
    public static final float MOVE_SPEED = 0.3f;
    public static final float MOVE_FACTOR = 0.5f;

    public static final int MAX_TURN_STATE = 7;

    private GameState gameState;
    public Location location;
    public EpochTiledMap currentMap;
    public Epoch game;

    public int turnState;
    public long turnNumber;

    //Handles our inputs
    public InputMultiplexer inputMultiplexer;

    //This is to determine where actors are drawn(For click detection)
    public static final int TILE_WIDTH = 394;
    public static final int TILE_HEIGHT = 455;

    public static final int SPRITE_SIZE = 300;

    //The view range of the player. This should (maybe) be moved to a specific player class
    public static final int PLAYER_VIEW_RANGE = 10;

    public enum Actions {
        MOVE
    }

    public enum GameState {
        MAIN_MENU,
        PAUSE_MENU,
        IN_GAME,
        OPTIONS_MENU,
        CREDITS
    }

    //Will need to be expanded to include every planet variation
    //Of planetary orbit and on planet
    public enum Location {
        OPEN_SPACE,
        PLANETARY_ORBIT,
        ON_PLANET
    }

    public enum Ships {
        //region Ship Definitions
        ASTRAL("ASTRAL", "Astral", Companies.ALARK, ShipType.FREIGHTER, 100 , 1.0f, 100, 6),
        ASTRALHV("ASTRAL_HV", "Astral Heavy", Companies.ALARK, ShipType.FREIGHTER, 100, 1.0f, 100, 6),
        GADRIN("GADRIN", "Gadrin", Companies.ALARK, ShipType.FREIGHTER, 100, 1.0f, 100, 6),
        BREAKER("BREAKER", "Breaker", Companies.ALARK, ShipType.MINER, 100, 1.0f, 100, 6),
        NOROTON("NOROTON", "Noroton", Companies.ALARK, ShipType.MINER, 100, 1.0f, 100, 6),
        IKERIUS("IKERIUS", "Ikerius", Companies.ALARK, ShipType.MINER, 100, 1.0f, 100, 6),

        ESKEL("ESKEL", "Eskel", Companies.CREST, ShipType.FREIGHTER, 100, 1.0f, 100, 6),
        NODON("NODON", "Nodon", Companies.CREST, ShipType.FREIGHTER, 100, 1.0f, 100, 6),
        KARLIG("KARLIG", "Karlig", Companies.CREST, ShipType.MINER, 100, 1.0f, 100, 6),
        ORGON("ORGON", "Orgon", Companies.CREST, ShipType.MINER, 100, 1.0f, 100, 6),
        INDELIN("INDELIN", "Indelin", Companies.CREST, ShipType.BUILDER, 100, 1.0f, 100, 6),
        CONTREX("CONTREX", "Contrex", Companies.CREST, ShipType.BUILDER, 100, 1.0f, 100, 6),

        HEXACRON("HEXACRON", "Hexacron", Companies.HALLEON, ShipType.FIGHTER, 100, 1.0f, 100, 6),
        IANDER("IANDER", "Iander", Companies.HALLEON, ShipType.FREIGHTER, 100, 1.0f, 100, 6),
        VEINER("VEINER", "Veiner", Companies.HALLEON, ShipType.MINER, 100, 1.0f, 100, 6),
        ALACRON("ALACRON", "Alacron", Companies.HALLEON, ShipType.MINER, 100, 1.0f, 100, 6),

        GENESIS("GENESIS", "Genesis", Companies.JDX, ShipType.FIGHTER, 100, 1.0f, 100, 6),
        OMEGA("OMEGA", "Omega", Companies.JDX, ShipType.FIGHTER, 100, 1.0f, 100, 6),

        PRIME("PRIME", "Prime", Companies.PARAGON, ShipType.FIGHTER, 100, 1.0f, 100, 6),
        HOME1A("HOME-1A", "Home-1A", Companies.PARAGON, ShipType.BUILDER, 100, 1.0f, 100, 6),

        RAZORBACK("RAZORBACK", "Razorback", Companies.VINDICATOR, ShipType.FIGHTER, 100, 1.0f, 100, 6),
        SHADOWBLADE("SHADOWBLADE", "Shadowblade", Companies.VINDICATOR, ShipType.FIGHTER, 100, 1.0f, 100, 6),
        CUTTHROAT("CUTTHROAT", "CutThroat", Companies.VINDICATOR, ShipType.FIGHTER, 100, 1.0f, 100, 6);
        //endregion

        public enum ShipType {
            FIGHTER,
            FREIGHTER,
            MINER,
            BUILDER
        }

        public enum Companies {
            ALARK("Alark Ships"),
            CREST("Crest Industries"),
            HALLEON("Halleon Crafts"),
            JDX("JDX"),
            PARAGON("Paragon"),
            REVOLUTION("Revolution Tech"),
            VINDICATOR("Vindicator Inc."),
            ZENITH("Zenith Robotics");

            private String name;

            Companies(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        private String atlasRegion;
        private String shipName;
        private Companies company;
        private ShipType type;
        private int health;
        private float armor;
        private int cargoSpace;
        private int speed;

        Ships(String atlasRegion, String name, Companies company, ShipType type,
              int health, float armor, int cargoSpace, int speed) {
            this.atlasRegion = atlasRegion;
            this.shipName = name;
            this.company = company;
            this.type = type;
            this.health = health;
            this.armor = armor;
            this.cargoSpace = cargoSpace;
            this.speed = speed;
        }

        public String getAtlasRegion() {
            return atlasRegion;
        }

        public String getShipName() {
            return shipName;
        }

        public Companies getCompany() {
            return company;
        }

        public ShipType getType() {
            return type;
        }

        public int getHealth() {
            return health;
        }

        public float getArmor() {
            return armor;
        }

        public int getCargoSpace() {
            return cargoSpace;
        }

        public int getSpeed() {
            return speed;
        }
    }

    public static GameManager getInstance() {
        return instance;
    }

    private GameManager() {
        //TODO Read from save files
        gameState = gameState.IN_GAME;
        location = Location.OPEN_SPACE;
        currentMap = new OpenSpaceMap();
        inputMultiplexer = new InputMultiplexer();
        turnState = 0;
        turnNumber = 0;
    }

    public void setGame(Epoch game) {
        this.game = game;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Sets the game state in the GameManager, updates the game,
     * and sets the input processor to whatever it should be
     * so that it switches the active screen
     * @param gameState the GameState to switch to
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        game.setActiveScreen(gameState);
        setInputProcessorGM(gameState);
    }

    /**
     * Defines the inputMultiplexer based off the current GameState
     * @param gameState the current GameState
     */
    public void setInputProcessorGM(GameState gameState) {
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
     * increments turn number
     * @return the new turn number
     */
    public int nextTurn() {
        turnState = 0;
        turnNumber++;
        return turnState;
    }

    /**
     * Increments the turn state, going to the next turn if required.
     * @return the new turn state
     */
    public int nextTurnState() {
        if(++turnState > MAX_TURN_STATE) {
            nextTurn();
            return turnState;
        }
        else {
            return turnState;
        }
    }
}
