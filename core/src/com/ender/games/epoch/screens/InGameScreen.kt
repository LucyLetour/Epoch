package com.ender.games.epoch.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.*
import com.ender.games.epoch.*
import com.ender.games.epoch.HexRoomConstants.HEX_DOORWAY_SIZE
import com.ender.games.epoch.HexRoomConstants.HEX_ROOM_SIZE
import com.ender.games.epoch.HexRoomConstants.HEX_ROOM_WALL_THICKNESS
import com.ender.games.epoch.HexRoomConstants.HEX_SPACING
import com.ender.games.epoch.HexRoomConstants.INTERIOR_HEX_SCALE
import com.ender.games.epoch.entities.*
import com.ender.games.epoch.entities.components.InputCodeComponent
import com.ender.games.epoch.entities.components.player
import com.ender.games.epoch.entities.systems.*
import com.ender.games.epoch.ship.weapons.HeavyAmmo
import com.ender.games.epoch.ship.weapons.LightAmmo
import com.ender.games.epoch.smoothCamera.SmoothCamSubject
import com.ender.games.epoch.smoothCamera.SmoothCamWorld
import com.ender.games.epoch.util.*
import com.ender.games.epoch.util.bloom.Bloom
import kotlin.math.*
import kotlin.random.Random

class InGameScreen(private val game: Epochkt): ScreenAdapter() {

    val world = World(Vector2(0f, 0f), true).apply {
        setContactListener(B2CollisionListener(this@InGameScreen))
    }

    lateinit var camWorld: SmoothCamWorld

    private var targetCameraZoom = 0.0f
    private var camDeltaX = 0.0f
    private var camDeltaY = 0.0f

    private val batch = SpriteBatch().apply { enableBlending() }
    private val wallRenderer = ShapeRenderer()
    private val guiBatch = SpriteBatch()
    private val font = BitmapFont()

    private val physicsDebugRenderer = Box2DDebugRenderer()

    val ui = Ui()

    val engine = PooledEngine().apply {
        addSystem(PhysicsSystem(world))
        addSystem(RenderSystem(batch, wallRenderer))
        addSystem(PlayerControllerSystem())
        addSystem(BulletSystem())
    }

    private val bloom = Bloom(Gdx.graphics.width, Gdx.graphics.height, false, false, true).apply {
        setBloomIntesity(5f)
        //setTreshold(0.2f)
    }

    var startTime: Long = 0
    val bm = BeatManager

    var curRoom: HexCoord = HexCoord(0, 0, 0)
    val clearedRooms = mutableListOf<>()

    init {
        engine.addEntity(InputCode.apply { add(InputCodeComponent()) })
        startTime = System.currentTimeMillis()
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
        wallRenderer.projectionMatrix = game.camera.combined

        //Handle camera zoom
        game.camera.zoom = 1f//Interpolation.fade.apply(game.camera.zoom, targetCameraZoom, ZOOM_SPEED)

        camWorld.update(delta)
        game.camera.position.set(Vector3(camWorld.pos.x, camWorld.pos.y, game.camera.position.z))

        bm.tick(delta)

        bloom.capture()

        run {
            engine.update(delta)
        }

        bloom.render()

        //Draw the GUI
        guiBatch.begin()
        run {
            if (true) {
                //draw FPS counter
                font.color = Color.GREEN
                font.draw(guiBatch, "FPS: " + Gdx.graphics.framesPerSecond, 50f, (game.viewport.screenHeight - 50).toFloat())
                font.draw(guiBatch, "Measure: " + BeatManager.measure, 50f, (game.viewport.screenHeight - 100).toFloat())
                font.draw(guiBatch, "Beat: " + BeatManager.beat, 50f, (game.viewport.screenHeight - 150).toFloat())
                font.color = Color.WHITE
            }
        }
        //ui.act(Gdx.graphics.deltaTime)
        //ui.draw()
        guiBatch.end()

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

        startTime = System.currentTimeMillis()
        bm.setVolume(.0f)
        bm.start()
    }

    private fun setupMap() {
        HexMap.gen(100)
        while(count(HexMap.head) < 25) {
            HexMap.gen(100)
        }
        //HexMap.gen(0)
        genRooms()
        println(count(HexMap.head))
    }

    private fun genRooms(room: Room = HexMap.head) {
        val hexPos = room.coord.toQR().toPoint().toVec2().scl(HEX_ROOM_SIZE / 100f).scl(HEX_SPACING)
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

                if(room.hasChild(i) || room.parentAt(i)) { // If the wall is connected to a child, spawn a door
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
                                    ((side / 2f) - (HEX_DOORWAY_SIZE / 2f)) / 2f,
                                    sideCenter.cpy().add(sideCenter.cpy().rotate90(1).nor().scl(doorCenterOffset)),
                                    Math.toRadians(i * 60.0).toFloat()
                            )
                        }
                    })
                    if(room.hasChild(i)) { // Create hallway
                        listOf(-1, 1).map {dir ->
                            createFixture(FixtureDef().apply {
                                shape = PolygonShape().apply {
                                    setAsBox(
                                            ((2f * apothem * HEX_SPACING) - 2f * apothem) / 2f,
                                            HEX_ROOM_WALL_THICKNESS,
                                            sideCenter.cpy().nor().scl(apothem * (1 * HEX_SPACING)).add(sideCenter.cpy().rotate90(dir).nor().scl(doorCenterOffset / 2f)),
                                            Math.toRadians(i * 60.0).toFloat()
                                    )
                                }
                            })
                        }
                    }
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

            createFixture(FixtureDef().apply {
                shape = PolygonShape().apply {
                    set(Array(6) { i -> Vector2(sin(i / 6f * TAU), cos(i / 6f * TAU)).scl(HEX_ROOM_SIZE - 1f) })
                }
            }).apply {
                isSensor = true
            }

            getInterior(room.type).forEach { def -> createFixture(def).apply {
                if(room.type == 5) userData = true
            } }

            this.fixtureList.forEach {
                engine.addEntity(createWall(it))
            }
        }

        room.children.forEach { genRooms(it) }
    }

    private fun getInterior(type: Int): List<FixtureDef> {
        val rndRot = Random.nextInt(0,5)
        @Suppress("DuplicatedCode")
        return when(type) {
            1 -> listOf(
                    FixtureDef().apply {
                        shape = PolygonShape().apply {
                            if(rndRot <= 2) set(Array(6) { i -> Vector2(cos(i / 6f * TAU), sin(i / 6f * TAU)).scl(INTERIOR_HEX_SCALE) })
                            else set(Array(6) { i -> Vector2(sin(i / 6f * TAU), cos(i / 6f * TAU)).scl(INTERIOR_HEX_SCALE) })
                        }
                    }
            )
            2 -> listOf(0, 1, 2, 3, 4, 5)
                    .map{ it + rndRot }
                    .chunked(2)
                    .map { list -> list.map { it.toFloat() } }
                    .map { fromto ->
                        FixtureDef().apply {
                            shape = PolygonShape().apply {
                                set(arrayOf(
                                    Vector2(0.8f * cos(fromto[0] / 6f * TAU), 0.8f * sin(fromto[0] / 6f * TAU)),
                                    Vector2(1.2f * cos(fromto[0] / 6f * TAU), 1.2f * sin(fromto[0] / 6f * TAU)),
                                    Vector2(1.2f * cos(fromto[1] / 6f * TAU), 1.2f * sin(fromto[1] / 6f * TAU)),
                                    Vector2(0.8f * cos(fromto[1] / 6f * TAU), 0.8f * sin(fromto[1] / 6f * TAU)),
                                ).map { it.scl(5f) }.toTypedArray<Vector2>())
                            }
                        }
                    }
            3 -> {
                val width =.3f
                val inner = HEX_ROOM_SIZE - width / sqrt(3f)
                val length = 10f
                listOf(
                    listOf(FixtureDef().apply { shape = CircleShape().apply { radius = 2f } }),
                    listOf(1,2,3,4,5,6).map {i ->
                        FixtureDef().apply {
                            shape = PolygonShape().apply {
                                val ang = TAU * (2 * i - 1) / 12f
                                val perp = Vector2(cos(ang), sin(ang)).nor().rotate90(1).scl(width/2f)
                                set(arrayOf(
                                        Vector2(length * cos(ang) + perp.x, length * sin(ang) + perp.y),
                                        Vector2(inner * cos(ang) + perp.x, inner * sin(ang) + perp.y),
                                        Vector2(inner * cos(ang) - perp.x, inner * sin(ang) - perp.y),
                                        Vector2(length * cos(ang) - perp.x, length * sin(ang) - perp.y),
                                ))
                            }
                        }
                    }
                ).flatten()
            }
            4 -> {
                val width = 2f
                val inner = HEX_ROOM_SIZE - width / sqrt(3f) / 2
                val length = 2f
                listOf(1,4).map {it + rndRot}.map { i ->
                    FixtureDef().apply {
                        shape = PolygonShape().apply {
                            val ang = TAU * (2 * i - 1) / 12f
                            val perp = Vector2(cos(ang), sin(ang)).nor().rotate90(1).scl(width/2f)
                            set(arrayOf(
                                    Vector2(length * cos(ang) + perp.x, length * sin(ang) + perp.y),
                                    Vector2(inner * cos(ang) + perp.x, inner * sin(ang) + perp.y),
                                    Vector2(inner * cos(ang) - perp.x, inner * sin(ang) - perp.y),
                                    Vector2(length * cos(ang) - perp.x, length * sin(ang) - perp.y),
                            ))
                        }
                    }
                }
            }
            5 -> listOf(1,2,3,4,5,6).map {i ->
                    FixtureDef().apply {
                        shape = CircleShape().apply {
                            radius = 6f
                            position = Vector2(cos(TAU * (2 * i - 1) / 12f), sin(TAU * (2 * i - 1) / 12f)).scl(HEX_ROOM_SIZE)
                        }
                    }
                }
            else-> listOf()
        }
    }
}