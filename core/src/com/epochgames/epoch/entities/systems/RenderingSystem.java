package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.components.IconComponent;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.util.Assets;
import com.epochgames.epoch.util.hexlib.Hexagon;
import com.epochgames.epoch.util.hexlib.Point;

public class RenderingSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Viewport viewport;

    private Array<Entity> renderQueue;

    public RenderingSystem(SpriteBatch batch, Viewport viewport) {
        super(Family.all(IconComponent.class, TransformComponent.class).get());

        this.batch = batch;
        this.viewport = viewport;

        renderQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        GameManager.getInstance().game.camera.update();

        batch.begin();
        batch.enableBlending();
        batch.setProjectionMatrix(GameManager.getInstance().game.camera.combined);

        for(Entity entity : renderQueue) {
            IconComponent iconComponent = entity.getComponent(IconComponent.class);
            TransformComponent transformComponent = entity.getComponent(TransformComponent.class);

            if(iconComponent == null) {
                //This Entity was not given an icon
                Gdx.app.debug("Entity without an Icon", "An Entity was attempted to be rendered, but had no Icon to render!");
                continue;
            }
            if(transformComponent == null) {
                System.out.println("dumbo");
            }

            float width = iconComponent.region.getRegionWidth();
            float height = iconComponent.region.getRegionHeight();
            float centerX = width / 2.0f;
            float centerY = height / 2.0f;
            Hexagon position = transformComponent.position;
            Point screenPosition = position.getHexCenter();

            GameManager.getInstance().game.camera.position.set(screenPosition.x - centerX, screenPosition.y - centerY, 0);

            batch.draw(iconComponent.region, GameManager.getInstance().game.camera.position.x,
                    GameManager.getInstance().game.camera.position.y, centerX, centerY, width,
                    height, transformComponent.scale * 2, transformComponent.scale * 2, transformComponent.rotation);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}