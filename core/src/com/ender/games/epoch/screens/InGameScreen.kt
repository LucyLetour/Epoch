package com.ender.games.epoch.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.ender.games.epoch.*
import com.ender.games.epoch.entities.Asteroid
import com.ender.games.epoch.entities.B2CollisionListener
import com.ender.games.epoch.entities.Player
import com.ender.games.epoch.entities.components.physics
import com.ender.games.epoch.entities.components.player
import com.ender.games.epoch.entities.systems.BulletSystem
import com.ender.games.epoch.entities.systems.PhysicsSystem
import com.ender.games.epoch.entities.systems.PlayerControllerSystem
import com.ender.games.epoch.entities.systems.RenderSystem
import com.ender.games.epoch.ship.weapons.LightAmmo
import com.ender.games.epoch.smoothCamera.SmoothCamSubject
import com.ender.games.epoch.smoothCamera.SmoothCamWorld
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.application.LibgdxApplication
import kotlin.math.max
import kotlin.math.min

class InGameScreen(private val game: Epochkt): ScreenAdapter() {

    var zirconApplication: LibgdxApplication
        private set

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

    val engine = PooledEngine().apply {
        addSystem(PhysicsSystem(world))
        addSystem(RenderSystem(batch))
        addSystem(PlayerControllerSystem())
        addSystem(BulletSystem())
    }

    init {
        val tileset = BuiltInCP437TilesetResource.WANDERLUST_16X16
        zirconApplication = LibgdxApplications.buildApplication(
                AppConfigs.newConfig()
                        .withDefaultTileset(tileset)
                        .withSize(Sizes.create(
                                23, //game.screenWidth / tileset.getWidth(),
                                63))//game.screenHeight / tileset.getHeight()))
                        .build()
        )
        zirconApplication.start()
        setupZircon()

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
        game.camera.zoom = 6f//Interpolation.fade.apply(game.camera.zoom, targetCameraZoom, ZOOM_SPEED)

        //Handle moving the camera
        //camDeltaX = physics.get(Player).body!!.position.x - game.camera.position.x
        //camDeltaY = physics.get(Player).body!!.position.y - game.camera.position.y
        //val targetPos = Vector3(physics.get(Player).body!!.position.x, physics.get(Player).body!!.position.y, 0f)

        //camDeltaX = Interpolation.linear.apply(camDeltaX, 0f, MOVE_SPEED)
        //camDeltaY = Interpolation.linear.apply(camDeltaY, 0f, MOVE_SPEED)
        //game.camera.translate(camDeltaX, camDeltaY)
        //val camPos = game.camera.position.scl(1f - delta)
        //targetPos.scl(delta)

        //val t = camPos.cpy()

        //println("delta = ${t.add(targetPos).sub(game.camera.position)}")

        camWorld.update(delta)
        //println("x=${camWorld.x}, y=${camWorld.y}")
        //game.camera.position.set(camPos.add(targetPos))
        game.camera.position.set(Vector3(camWorld.pos.x, camWorld.pos.y, game.camera.position.z))
        //Draw the tile actors
        //tileActorStage.draw()

        batch.begin()
        run {
            engine.update(delta)
        }
        batch.end()

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
        guiBatch.end()

        zirconApplication.render()

        physicsDebugRenderer.render(world, game.camera.combined)

        game.camera.update()
    }

    override fun show() {
        game.camera.zoom = 6f
        targetCameraZoom = START_ZOOM
        camWorld = SmoothCamWorld(player.get(Player).smoothCamSubject)
        engine.addEntity(Player)
        repeat(100) {
            engine.addEntity(Asteroid((-100..100).random().toFloat(), (-100..100).random().toFloat()))
        }
        Player.inventory.addItem(LightAmmo())
        Player.inventory.addItem(LightAmmo())
    }

    fun zoom(delta: Float) {
        targetCameraZoom = max(min(targetCameraZoom + delta, MAX_ZOOM), MIN_ZOOM)
    }

    fun scroll(deltaX: Float, deltaY: Float) {
        //camDeltaX += deltaX// * MOVE_FACTOR// * game.camera.zoom / 2f
        //camDeltaY += deltaY// * MOVE_FACTOR// * game.camera.zoom / 2f
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