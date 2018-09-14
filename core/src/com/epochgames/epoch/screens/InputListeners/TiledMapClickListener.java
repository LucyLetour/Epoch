package com.epochgames.epoch.screens.InputListeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.TileMapActor;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TransformComponent;
import com.epochgames.epoch.entities.components.TypeComponent;
import com.epochgames.epoch.util.hexlib.HexHelper;
import com.epochgames.epoch.util.hexlib.Hexagon;
import com.epochgames.epoch.util.hexlib.OffsetCoord;

public class TiledMapClickListener extends ClickListener {
    private TileMapActor actor;
    public Epoch game;

    public TiledMapClickListener(TileMapActor actor, Epoch game) {
        this.actor = actor;
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        switch (GameManager.getInstance().game.inGameScreen.currentAction) {
            case MOVE:
                for(Entity entity : game.inGameScreen.engine.getEntitiesFor(Family.all(TypeComponent.class).get())) {
                    //If the entity is the player and they are not already moving
                    if(entity.getComponent(TypeComponent.class).type == TypeComponent.PLAYER &&
                            entity.getComponent(MoveComponent.class).isMoving == false) {
                        entity.getComponent(MoveComponent.class).nextPosition = new Hexagon(HexHelper.getCoordinatesFromHexCenter(actor.position));
                        entity.getComponent(MoveComponent.class).shouldMove = true;
                    }
                }
                break;
            default:
                break;
        }
    }
}
