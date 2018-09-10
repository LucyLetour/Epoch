package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epochgames.epoch.entities.components.IconComponent;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.TransformComponent;

public class RenderingSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Viewport viewport;

    private Array<Entity> renderQueue;

    public RenderingSystem(SpriteBatch batch, Viewport viewport) {
        super(Family.all().get());

        this.batch = batch;
        this.viewport = viewport;

        renderQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        batch.enableBlending();

        for(Entity entity : renderQueue) {
            IconComponent iconComponent = Mappers.icon.get(entity);
            TransformComponent transformComponent = Mappers.transform.get(entity);

            if(iconComponent == null) {
                continue;
            }

            float width = iconComponent.region.getRegionWidth();
            float height = iconComponent.region.getRegionHeight();
            float originX = width / 2.0f;
            float originY = height / 2.0f;

            batch.draw(iconComponent.region, transformComponent.position.x - originX,
                    transformComponent.position.y - originY, originX, originY, width,
                    height, transformComponent.scale, transformComponent.scale, transformComponent.rotation);
        }

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}