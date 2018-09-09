package com.epochgames.epoch.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.Program;
import com.epochgames.epoch.entities.components.IconComponent;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.entities.systems.RenderingSystem;
import com.epochgames.epoch.maps.OpenSpaceMap;
import com.epochgames.epoch.util.Assets;
import com.epochgames.epoch.util.EpochMath;

public class InGame extends ScreenAdapter {

    private Stage tileActorStage;
    private GameManager gameManager;

    public RenderingSystem renderingSystem;

    Engine engine;

    public Program game;

    public OpenSpaceMap openSpaceMap;

    public float targetCameraZoom;

    public InGame(Program game) {
        gameManager = GameManager.getInstance();
        openSpaceMap = new OpenSpaceMap();
        this.game = game;

        engine = new Engine();
        renderingSystem = new RenderingSystem(game.batch, game.viewport);
        t_createEntity();
        engine.addSystem(renderingSystem);


    }

    @Override
    public void render(float delta) {
        //GL Stuff
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update and set projection matrix
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        //Handle camera zoom
        game.camera.zoom = Interpolation.fade.apply(game.camera.zoom, targetCameraZoom, gameManager.ZOOM_SPEED);

        //Render the tilemap based on the appropriate position of the player
        switch (gameManager.getLocation()) {
            case OPEN_SPACE:
                openSpaceMap.render(game.camera);
                break;
            case PLANETARY_ORBIT:
                break;
            case ON_PLANET:
                break;
            default:
                Gdx.app.error("Error", "No map to be loaded because the location doesn't exist!");
                break;
        }

        //Draw everything the game needs
        game.batch.begin();
        {
            engine.update(delta);
        }
        game.batch.end();

        //Draw the GUI
        game.guiBatch.begin();
        {

        }
        game.guiBatch.end();
    }

    @Override
    public void show() {
        //Cool effect that zooms in on our grid when the game is initialized
        game.camera.zoom = 10.0f;
        targetCameraZoom = gameManager.START_ZOOM;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void zoom(float delta) {
        targetCameraZoom = (float)EpochMath.clamp(targetCameraZoom + delta, 1.0f, 4.0f);
    }

    public void t_createEntity() {
        Entity entity = new Entity();

        IconComponent icon = new IconComponent();
        TransformComponent transform = new TransformComponent();

        icon.region = Assets.MANAGER.get(Assets.Spritesheets.SHIPS).findRegion(Assets.SHIP_ATLAS_REGIONS.ALACRON.getAtlasRegion());
        transform.position.set(game.camera.position.x, game.camera.position.y);

        entity.add(icon);
        entity.add(transform);

        engine.addEntity(entity);
    }
}
