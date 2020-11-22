package com.ender.games.epoch

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ender.games.epoch.screens.InGameScreen
import com.ender.games.epoch.screens.MainMenuScreen
import com.ender.games.epoch.screens.inputListeners.InGameInputListener
//import com.ender.games.epoch.screens.inputListeners.InGameInputListener
import org.hexworks.zircon.internal.listeners.ZirconInputListener
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt

//Controls speed of zoom. Lower is slower
const val ZOOM_FACTOR = 0.2f
const val ZOOM_SPEED = 0.2f
const val START_ZOOM = 2f
const val MIN_ZOOM = 0.1f
const val MAX_ZOOM = 0.7f

const val BULLET_LIFETIME = 10_000L

object HexRoomConstants {
    const val HEX_ROOM_SIZE = 20f
    //val INTERNAL_HEX_SIZE = HEX_ROOM_SIZE * sqrt(3f) / 2
    const val HEX_ROOM_WALL_THICKNESS = 0.2f
    const val HEX_DOORWAY_SIZE = 8f
    const val HEX_SPACING = 1.8f
    const val INTERIOR_HEX_SCALE = 6f
}

// E(B) where B is the event of a side of a room having a branch
const val E_BRANCHES = 2

const val TAU = 2f * PI.toFloat()

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

object GAME_MANAGER {
    var gameState: GameState = GameState.MAIN_MENU
        private set
    var location = Location.OPEN_SPACE
        private set
    private val inputMultiplexer = InputMultiplexer()

    var game: Epochkt? = null

    /**
     * Sets the game state in the GameManager, updates the game,
     * and sets the input processor to whatever it should be
     * @param gameState the GameState to switch to
     */
    fun setGameState(gameState: GameState) {
        GAME_MANAGER.gameState = gameState
        game?.setActiveScreen(gameState) ?: throw Exception("Game was not initialized!")
        setInputProcessorGM(gameState)
    }

    /**
     * Defines the inputMultiplexer based off the current GameState
     * @param gameState the current GameState
     */
    private fun setInputProcessorGM(gameState: GameState) {
        inputMultiplexer.clear()

        when (gameState) {
            GameState.IN_GAME -> {
                with(game!!.screen as InGameScreen) {
                    inputMultiplexer.addProcessor(this.ui)
                    inputMultiplexer.addProcessor(InGameInputListener(this))
                }
            }
            GameState.MAIN_MENU -> {
                with(game!!.screen as MainMenuScreen) {
                    inputMultiplexer.addProcessor(this.ui)
                }
            }
            else -> {
                Gdx.app.error("Invalid Game State", "Game State was invalid, meaning input processors could not be assigned!")
                Gdx.app.exit()
                throw RuntimeException()
            }
        }

        Gdx.input.inputProcessor = inputMultiplexer
    }
}

enum class Ships(val atlasRegion: String, val shipName: String, val company: Companies, val type: ShipType,
                 val health: Int, val armor: Float, val cargoSpace: Int, val speed: Int, val weaponSlots: Int) {
    //region Ship Definitions
    ASTRAL("ASTRAL", "Astral", Companies.ALARK, ShipType.FREIGHTER, 100, 1.0f, 100, 6, 1),
    ASTRALHV("ASTRAL_HV", "Astral Heavy", Companies.ALARK, ShipType.FREIGHTER, 100, 1.0f, 100, 6, 1),
    GADRIN("GADRIN", "Gadrin", Companies.ALARK, ShipType.FREIGHTER, 100, 1.0f, 100, 6, 1),
    BREAKER("BREAKER", "Breaker", Companies.ALARK, ShipType.MINER, 100, 1.0f, 100, 6, 1),
    NOROTON("NOROTON", "Noroton", Companies.ALARK, ShipType.MINER, 100, 1.0f, 100, 6, 1),
    IKERIUS("IKERIUS", "Ikerius", Companies.ALARK, ShipType.MINER, 100, 1.0f, 100, 6, 1),

    ESKEL("ESKEL", "Eskel", Companies.CREST, ShipType.FREIGHTER, 100, 1.0f, 100, 6, 1),
    NODON("NODON", "Nodon", Companies.CREST, ShipType.FREIGHTER, 100, 1.0f, 100, 6, 1),
    KARLIG("KARLIG", "Karlig", Companies.CREST, ShipType.MINER, 100, 1.0f, 100, 6, 1),
    ORGON("ORGON", "Orgon", Companies.CREST, ShipType.MINER, 100, 1.0f, 100, 6, 1),
    INDELIN("INDELIN", "Indelin", Companies.CREST, ShipType.BUILDER, 100, 1.0f, 100, 6, 1),
    CONTREX("CONTREX", "Contrex", Companies.CREST, ShipType.BUILDER, 100, 1.0f, 100, 6, 1),

    HEXACRON("HEXACRON", "Hexacron", Companies.HALLEON, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1),
    IANDER("IANDER", "Iander", Companies.HALLEON, ShipType.FREIGHTER, 100, 1.0f, 100, 6, 1),
    VEINER("VEINER", "Veiner", Companies.HALLEON, ShipType.MINER, 100, 1.0f, 100, 6, 1),
    ALACRON("ALACRON", "Alacron", Companies.HALLEON, ShipType.MINER, 100, 1.0f, 100, 6, 1),

    GENESIS("GENESIS", "Genesis", Companies.JDX, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1),
    OMEGA("OMEGA", "Omega", Companies.JDX, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1),

    PRIME("PRIME", "Prime", Companies.PARAGON, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1),
    HOME1A("HOME-1A", "Home-1A", Companies.PARAGON, ShipType.BUILDER, 100, 1.0f, 100, 6, 1),

    RAZORBACK("RAZORBACK", "Razorback", Companies.VINDICATOR, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1),
    SHADOWBLADE("SHADOWBLADE", "Shadowblade", Companies.VINDICATOR, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1),
    CUTTHROAT("CUTTHROAT", "CutThroat", Companies.VINDICATOR, ShipType.FIGHTER, 100, 1.0f, 100, 6, 1);
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