package com.epochgames.epoch.screens.inputListeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.GameManager;
import com.epochgames.epoch.entities.TileMapActor;
import com.epochgames.epoch.entities.components.DialogueComponent;
import com.epochgames.epoch.entities.components.Mappers;
import com.epochgames.epoch.entities.components.MoveComponent;
import com.epochgames.epoch.entities.components.TypeComponent;
import com.epochgames.epoch.util.hexlib.HexSatelliteData;
import org.hexworks.mixite.core.api.Hexagon;
import org.hexworks.mixite.core.api.contract.SatelliteData;

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
                            !entity.getComponent(MoveComponent.class).isMoving) {
                        entity.getComponent(MoveComponent.class).nextPosition =
                                game.inGameScreen.hexagonGrid.hexGrid.getByPixelCoordinate(actor.position.x, actor.position.y).get().getCubeCoordinate();
                        if(entity.getComponent(MoveComponent.class).currentPosition.equals(entity.getComponent(MoveComponent.class).nextPosition)) {
                            return;
                        }
                        entity.getComponent(MoveComponent.class).shouldMove = true;
                    }
                }
                break;
            case INTERACT:
                Hexagon<HexSatelliteData> selectedHex = game.inGameScreen.hexagonGrid.hexGrid.getByPixelCoordinate(actor.position.x, actor.position.y).get();
                if(selectedHex.getSatelliteData().isPresent() && selectedHex.getSatelliteData().get().getEntityContained() != null) {
                    Entity selectedEntity = selectedHex.getSatelliteData().get().getEntityContained();
                    if(selectedEntity.getComponent(DialogueComponent.class) != null) {
                        game.inGameScreen.dialogueEngine.actDialogue(Mappers.dialogue.get(selectedEntity).dialogueScript);
                    }
                }
                break;
            default:
                break;
        }
    }
}
