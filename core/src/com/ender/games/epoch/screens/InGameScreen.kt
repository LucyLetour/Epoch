package com.ender.games.epoch.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.*
import com.ender.games.epoch.*
import com.ender.games.epoch.entities.*
import com.ender.games.epoch.entities.components.InputCodeComponent
import com.ender.games.epoch.entities.components.player
import com.ender.games.epoch.entities.systems.*
import com.ender.games.epoch.ship.weapons.HeavyAmmo
import com.ender.games.epoch.ship.weapons.LightAmmo
import com.ender.games.epoch.smoothCamera.SmoothCamSubject
import com.ender.games.epoch.smoothCamera.SmoothCamWorld
import com.ender.games.epoch.util.HexMap
import com.ender.games.epoch.util.Room
import com.ender.games.epoch.util.bloom.Bloom
import com.ender.games.epoch.util.toPoint
import com.ender.games.epoch.util.toVec2
import kotlin.math.*

class InGameScreen(private val game: Epochkt): ScreenAdapter() {

    //var zirconApplication: LibgdxApplication
    //    private set

    val world = World(Vector2(0f, 0f), true).apply {
        setContactListener(B2CollisionListener())
    }

    lateinit var smoothCamSubject: SmoothCamSubject
    lateinit var camWorld: SmoothCamWorld

    private var targetCameraZoom = 0.0f
    private var camDeltaX = 0.0f
    private var camDeltaY = 0.0f

    private val batch = SpriteBatch().apply { enableBlending() }
    private val guiBatch = SpriteBatch()
    private val font = BitmapFont()

    private val physicsDebugRenderer = Box2DDebugRenderer()

    val ui = Ui()

    val engine = PooledEngine().apply {
        addSystem(PhysicsSystem(world))
        addSystem(RenderSystem(batch))
        addSystem(PlayerControllerSystem())
        addSystem(BulletSystem())
    }

    val bloom = Bloom()

    init {
        /*val tileset = BuiltInCP437TilesetResource.WANDERLUST_16X16
        zirconApplication = LibgdxApplications.buildApplication(
                AppConfigs.newConfig()
                        .withDefaultTileset(tileset)
                        .withSize(Sizes.create(
                                23, //game.screenWidth / tileset.getWidth(),
                                63))//game.screenHeight / tileset.getHeight()))
                        .build()
        )
        zirconApplication.start()
        setupZircon()*/
        engine.addEntity(InputCode.apply { add(InputCodeComponent()) })
        setupMap()
    }

    override fun render(delta: Float) {
        super.render(delta)

        //GL Stuff
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //Update and set projection matrix
        batch.projectionMatrix = game.camera.combined

        //Handle camera zoom
        game.camera.zoom = 1f//Interpolation.fade.apply(game.camera.zoom, targetCameraZoom, ZOOM_SPEED)

        camWorld.update(delta)
        game.camera.position.set(Vector3(camWorld.pos.x, camWorld.pos.y, game.camera.position.z))

        bloom.capture()

        batch.begin()
        run {
            engine.update(delta)
        }
        batch.end()

        bloom.render()

        //Draw the GUI
        guiBatch.begin()
        run {
            if (game.debug) {
                //draw FPS counter
                font.color = Color.GREEN
                font.draw(guiBatch, "FPS: " + Gdx.graphics.framesPerSecond, 50f, (game.viewport.screenHeight - 50).toFloat())
                font.color = Color.WHITE
            }
        }
        ui.act(Gdx.graphics.deltaTime)
        ui.draw()
        guiBatch.end()

        //zirconApplication.render()

        physicsDebugRenderer.render(world, game.camera.combined)

        game.camera.update()
    }

    override fun show() {
        game.camera.zoom = 6f
        targetCameraZoom = START_ZOOM
        camWorld = SmoothCamWorld(player.get(Player).smoothCamSubject)
        engine.addEntity(Player)
        //val s = createShip(Ships.ALACRON)
        //engine.addEntity(s.entity)
        //engine.addEntity(createShip(Ships.CONTREX).entity)
        //removeShip(s)
        //repeat(100) {
        //    engine.addEntity(Asteroid((-1000..1000).random().toFloat(), (-1000..1000).random().toFloat()))
        //}
        Player.inventory.addItem(LightAmmo())
        Player.inventory.addItem(HeavyAmmo())
    }

    fun zoom(delta: Float) {
        targetCameraZoom = max(min(targetCameraZoom + delta, MAX_ZOOM), MIN_ZOOM)
    }

    private fun setupMap() {
        HexMap.gen(5)
        genBodies()
    }

    private fun genBodies(room: Room = HexMap.head) {
        val hexPos = room.coord.toQR().toPoint().toVec2().scl(HEX_ROOM_SIZE / 100f)
        val side = HEX_ROOM_SIZE
        val apothem = side * sqrt(3f) / 2f

        val doorCenterOffset = (HEX_DOORWAY_SIZE / 2f) + (side / 2f - HEX_DOORWAY_SIZE / 2f) / 2f

        world.createBody(BodyDef().apply {
            type = BodyDef.BodyType.StaticBody
            position.set(hexPos.scl(100f))
        }).apply {
            for(i in 0..5) {
                val sideCenter = Vector2(
                        apothem * cos(Math.toRadians(i * 60.0)).toFloat(),
                        apothem * sin(Math.toRadians(i * 60.0)).toFloat()
                )
                if(!room.parentAt(i)) { // If the wall comes from the parent, don't spawn any walls (saves 2 boxes)
                    if(room.hasChild(i)) { // If the wall is connected to a child, spawn a door
                        createFixture(FixtureDef().apply {
                            shape = PolygonShape().apply {
                                setAsBox(
                                        HEX_ROOM_WALL_THICKNESS / 2f,
                                        ((side / 2f) - (HEX_DOORWAY_SIZE / 2f)) / 2f,
                                        sideCenter.cpy().add(sideCenter.cpy().rotate90(-1).nor().scl(doorCenterOffset)),
                                        Math.toRadians(i * 60.0).toFloat()
                                )
                            }
                        })
                        createFixture(FixtureDef().apply {
                            shape = PolygonShape().apply {
                                setAsBox(
                                        HEX_ROOM_WALL_THICKNESS / 2f,
                                        (side  / 2f - HEX_DOORWAY_SIZE / 2f) / 2f,
                                        sideCenter.cpy().add(sideCenter.cpy().rotate90(1).nor().scl(doorCenterOffset)),
                                        Math.toRadians(i * 60.0).toFloat()
                                )
                            }
                        })
                    } else { // Spawn a regular, solid wall
                        createFixture(FixtureDef().apply {
                            shape = PolygonShape().apply {
                                setAsBox(
                                        HEX_ROOM_WALL_THICKNESS / 2f,
                                        side / 2f,
                                        sideCenter,
                                        Math.toRadians(i * 60.0).toFloat()
                                )
                            }
                        })
                    }
                }
            }
        }

        room.children.forEach { genBodies(it) }
    }

    private fun setupZircon() {
        /*val screen = Screens.createScreenFor(zirconApplication.tileGrid)

        val panel = Components.panel()
                .wrapWithBox(true)
                .withTitle("Test Window")
                .withSize(Sizes.create(20, 60))
                .withPosition(Positions.create(3, 3))
                .build()

        val header = Components.header()
                .withPosition(Positions.offset1x1())
                .withText("Header")
                .build()

        val checkBox = Components.checkBox()
                .withText("Check me!")
                .withPosition(Positions.create(0, 1)
                        .relativeToBottomOf(header))
                .build()

        panel.addComponent(header)
        panel.addComponent(checkBox)
        screen.addComponent(panel)
        panel.applyColorTheme(ColorThemes.afterTheHeist())
        screen.display()*/
    }
}