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
import com.epochgames.epoch.entities.components.TransformComponent;
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

            float width = iconComponent.region.getRegionWidth();
            float height = iconComponent.region.getRegionHeight();
            float centerX = width / 2.0f;
            float centerY = height / 2.0f;
            Point screenPosition = transformComponent.position.getHexCenter();

            batch.draw(iconComponent.region, screenPosition.x - centerX,
                    screenPosition.y - centerY, centerX, centerY, width,
                    height, transformComponent.scale, transformComponent.scale, transformComponent.rotation);
        }
        batch.end();
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}