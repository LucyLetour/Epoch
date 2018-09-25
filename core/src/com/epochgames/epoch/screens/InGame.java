package com.epochgames.epoch.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.EntityFactory;
import com.epochgames.epoch.entities.Ship;
import com.epochgames.epoch.entities.systems.MovementSystem;
import com.epochgames.epoch.entities.systems.RenderingSystem;
import com.epochgames.epoch.entities.systems.RotationSystem;
import com.epochgames.epoch.entities.systems.TurnSystem;
import com.epochgames.epoch.maps.OpenSpaceMap;
import com.epochgames.epoch.screens.stages.TiledMapStage;
import com.epochgames.epoch.util.Assets;
import com.epochgames.epoch.util.EpochMath;
import com.epochgames.epoch.util.HexMapRender.HexMapRenderer;
import com.epochgames.epoch.util.HexagonGrid;
import com.epochgames.epoch.util.hexlib.*;
import org.codetome.hexameter.core.api.CubeCoordinate;

public class InGame extends ScreenAdapter {

    public Stage tileActorStage;
    public GameManager gameManager;

    public RenderingSystem renderingSystem;
    public MovementSystem movementSystem;
    public RotationSystem rotationSystem;
    public TurnSystem turnSystem;

    public Engine engine;

    public Epoch game;

    public OpenSpaceMap openSpaceMap;
    public HexagonGrid hexagonGrid;
    public HexMapRenderer mapRenderer;

    public float targetCameraZoom;
    public float camDeltaX;
    public float camDeltaY;

    public GameManager.Actions currentAction;

    public InGame(Epoch game) {
        this.game = game;

        //Get the game manager and create a map to start on
        gameManager = GameManager.getInstance();
        //TODO this needs to be based off the state in the save file (Game manager)
        openSpaceMap = new OpenSpaceMap();

        //Create our hexgrid, which will act as a way to place objects "on" our tilemap
        hexagonGrid = new HexagonGrid(openSpaceMap.getTiledMap());
        mapRenderer = HexMapRenderer.instance;
        mapRenderer.setHexGrid(hexagonGrid.hexGrid, game, Assets.MANAGER.get(Assets.Textures.HEX_TILE));

        //Start our engine and add all the necessary systems
        engine = new Engine();

        renderingSystem = new RenderingSystem(game.batch);
        movementSystem = new MovementSystem();
        rotationSystem = new RotationSystem();
        turnSystem = new TurnSystem(gameManager);

        engine.addSystem(renderingSystem);
        engine.addSystem(movementSystem);
        engine.addSystem(rotationSystem);

        //Create a stage for the clickable things
        tileActorStage = new TiledMapStage(openSpaceMap.getTiledMap(), hexagonGrid.hexGrid);
        tileActorStage.setViewport(game.viewport);

        //Initialize the Entity Factory so we can create entities OTF
        EntityFactory.init(game);

        //Temp
        engine.addEntity(EntityFactory.createShip(CubeCoordinate.fromCoordinates(9, 5), hexagonGrid, new Ship(GameManager.Ships.CONTREX, false), true));
        currentAction = GameManager.Actions.MOVE;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //GL Stuff
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update and set projection matrix
        game.batch.setProjectionMatrix(game.camera.combined);

        //Handle camera zoom
        game.camera.zoom = Interpolation.fade.apply(game.camera.zoom, targetCameraZoom, GameManager.ZOOM_SPEED);

        //Handle moving the camera
        camDeltaX = Interpolation.smoother.apply(camDeltaX, 0, GameManager.MOVE_SPEED);
        camDeltaY = Interpolation.smoother.apply(camDeltaY, 0, GameManager.MOVE_SPEED);
        game.camera.translate(camDeltaX, camDeltaY);




        //Draw everything the game needs
        tileActorStage.draw();

        /*if(t_p1 != null && t_p2 != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setProjectionMatrix(game.camera.combined);
            for (int i = 0; i < hexPath.length - 1; i++) {
                shapeRenderer.line(hexPath[i].getHexCenter().x, hexPath[i].getHexCenter().y, hexPath[i + 1].getHexCenter().x, hexPath[i + 1].getHexCenter().y);
                if(!t_printed) {
                    System.out.println(hexPath[i].offsetCoord);
                }
            }
            t_printed = true;
            shapeRenderer.end();
        }*/

        game.batch.begin();
        {
            //Render the tilemap based on the appropriate position of the player
            switch (gameManager.getLocation()) {
                case OPEN_SPACE:
                    //openSpaceMap.render(game.camera);
                    mapRenderer.renderHexGrid();
                    break;
                case PLANETARY_ORBIT:
                    break;
                case ON_PLANET:
                    break;
                default:
                    Gdx.app.error("Error", "No map to be loaded because the location doesn't exist!");
                    break;
            }
            for (org.codetome.hexameter.core.api.Hexagon<HexSatelliteData> hexagon : hexagonGrid.hexGrid.getHexagons()){
                game.font.draw(game.batch, HexHelper.cubeToOddR(new CubeCoord(hexagon.getGridX(), hexagon.getGridY(), hexagon.getGridZ())).toString(),(float)hexagon.getCenterX(), (float)hexagon.getCenterY());
            }
            engine.update(delta);
        }
        game.batch.end();

        //Draw the GUI
        game.guiBatch.begin();
        {
            game.font.setColor(Color.GREEN);
            game.font.draw(game.guiBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 50, game.viewport.getScreenHeight() - 50);
            game.font.setColor(Color.WHITE);
        }
        game.guiBatch.end();

        game.camera.update();
    }

    @Override
    public void show() {
        //Cool effect that zooms in on our grid when the game is initialized
        game.camera.zoom = 6.0f;
        targetCameraZoom = GameManager.START_ZOOM;
    }

    @Override
    public void dispose() {
        tileActorStage.dispose();
    }

    /**
     * Zooms the camera based off mousewheel input. The zoom is clamped to prevent
     * zooming too far/close
     * @param delta the amount to change the zoom
     */
    public void zoom(float delta) {
        targetCameraZoom = (float)EpochMath.clamp(targetCameraZoom + delta, GameManager.MIN_ZOOM, GameManager.MAX_ZOOM);
    }

    public void scroll(float deltaX, float deltaY) {
        camDeltaX += deltaX;
        camDeltaY += deltaY;
    }
}