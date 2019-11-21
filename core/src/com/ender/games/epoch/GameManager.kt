package com.epochgames.epoch

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.epochgames.epoch.screens.InGame
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.listeners.ZirconInputListener

//Controls speed of zoom. Lower is slower
const val ZOOM_FACTOR = 0.2f
const val ZOOM_SPEED = 0.2f
const val START_ZOOM = 2.0f
const val MIN_ZOOM = 1.0f
const val MAX_ZOOM = 4.0f

//Controls speed of camera for moving
const val MOVE_SPEED = 0.3f
const val MOVE_FACTOR = 0.5f

//This is to determine where actors are drawn(For click detection)
val TILE_WIDTH = 394
val TILE_HEIGHT = 455

val SPRITE_SIZE = 300
val PLANET_PADDING = 100

//The view range and speed of the player. This should (maybe) be moved to a specific player class
val PLAYER_VIEW_RANGE = 10
val SHIP_SPEED = 1000.0f

enum class Actions {
    MOVE,
    INTERACT
}

enum class GameState {
    MAIN_MENU,
    PAUSE_MENU,
    IN_GAME,
    OPTIONS_MENU,
    CREDITS
}

//Will need to be expanded to include every planet variation
//Of planetary orbit and on planet
enum class Location {
    OPEN_SPACE,
    PLANETARY_ORBIT,
    ON_PLANET
}

object GameManager {
    private var gameState = GameState.IN_GAME
    private val location = Location.OPEN_SPACE
    private val inputMultiplexer = InputMultiplexer()

    private var game: Epochkt? = null

    /**
     * Sets the game state in the GameManager, updates the game,
     * and sets the input processor to whatever it should be
     * so that it switches the active screen
     * @param gameState the GameState to switch to
     */
    fun setGameState(gameState: GameState) {
        this.gameState = gameState
        game?.setActiveScreen(gameState) ?: throw Exception("Game was not initialized!")
        setInputProcessorGM(gameState)
    }

    /**
     * Defines the inputMultiplexer based off the current GameState
     * @param gameState the current GameState
     */
    fun setInputProcessorGM(gameState: GameState) {
        val screenProcessor: InputProcessor
        val stageProcessor: Stage
        val zirconInputProcessor: InputProcessor

        when (gameState) {
            GameManager.GameState.IN_GAME -> {
                screenProcessor = InGameInputListener(game.activeScreen as InGame)
                stageProcessor = (game.activeScreen as InGame).tileActorStage
                zirconInputProcessor = ZirconInputListener(
                        (game.activeScreen as InGame).zirconApplication.tileGrid.currentTileset().width,
                        (game.activeScreen as InGame).zirconApplication.tileGrid.currentTileset().height)
            }
            else -> {
                Gdx.app.error("Invalid Game State", "Game State was invalid, " + "meaning input processors could not be assigned!")
                Gdx.app.exit()
                throw RuntimeException()
            }
        }


        inputMultiplexer.addProcessor(screenProcessor)
        inputMultiplexer.addProcessor(stageProcessor)
        inputMultiplexer.addProcessor(zirconInputProcessor)

        Gdx.input.inputProcessor = inputMultiplexer
    }
}

enum class Ships(val atlasRegion: String, val shipName: String, val company: Companies, val type: ShipType,
                                     val health: Int, val armor: Float, val cargoSpace: Int, val speed: Int) {
    //region Ship Definitions
    ASTRAL("ASTRAL", "Astral", Companies.ALARK, ShipType.FREIGHTER, 100, 1.0f, 100, 6),
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

    enum class ShipType {
        FIGHTER,
        FREIGHTER,
        MINER,
        BUILDER
    }

    enum class Companies(val company: String) {
        ALARK("Alark Ships"),
        CREST("Crest Industries"),
        HALLEON("Halleon Crafts"),
        JDX("JDX"),
        PARAGON("Paragon"),
        REVOLUTION("Revolution Tech"),
        VINDICATOR("Vindicator Inc."),
        ZENITH("Zenith Robotics")
    }
}

enum class Planets(val atlasRegion: String, val orbitPoint: Planets?, val size: Int) {
    //region Planet Definitions
    RHEDIAN("RHEDIAN", null, 5),
    EREAS("EREAS", RHEDIAN, 1),
    SKRALLEN("SKRALLEN", RHEDIAN, 2),
    ONATH("ONATH", SKRALLEN, 1),
    ILLUSTIRADE("ILLUSTIRADE", RHEDIAN, 2),
    CESSNIA("CESSNIA", RHEDIAN, 1),
    KORB("KORB", RHEDIAN, 3),
    ALLIE("ALLIE", KORB, 1),
    UFNO("UFNO", KORB, 1),
    ZERNO("ZERNO", KORB, 1),
    YALDIN_PARIAH("YALDIN_PARIAH", RHEDIAN, 2)
}
