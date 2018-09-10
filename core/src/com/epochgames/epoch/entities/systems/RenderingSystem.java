package com.epochgames.epoch.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
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
        super(Family.all(IconComponent.class, TransformComponent.class).get());

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
                //This Entity was not given an icon
                Gdx.app.debug("Entity without an Icon", "An Entity was attempted to be rendered, but had no Icon to render!");
                continue;
            }

            float width = iconComponent.region.getRegionWidth();
            float height = iconComponent.region.getRegionHeight();
            float centerX = width / 2.0f;
            float centerY = height / 2.0f;

            //float hexCenterX =

            batch.draw(iconComponent.region, 1,
                    1, centerX, centerY, width,
                    height, transformComponent.scale, transformComponent.scale, transformComponent.rotation);
        }

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}