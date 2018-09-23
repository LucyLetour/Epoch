package com.epochgames.epoch.screens.inputListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epochgames.epoch.Epoch;
import com.epochgames.epoch.entities.TileMapActor;
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
        if(game.inGameScreen.t_hexZero != null) {
            if (game.inGameScreen.t_hexOne == null) {
                game.inGameScreen.t_hexOne = new Hexagon(HexHelper.getCoordinatesFromHexCenter(actor.position));
                game.inGameScreen.t_p1 = actor.position;
                System.out.println("Set hex one to " + game.inGameScreen.t_hexOne.offsetCoord.toString());
            } else if (game.inGameScreen.t_hexOne != null) {
                game.inGameScreen.t_hexTwo = new Hexagon(HexHelper.getCoordinatesFromHexCenter(actor.position));
                game.inGameScreen.t_p2 = actor.position;
                System.out.println("Set hex two to " + game.inGameScreen.t_hexTwo.offsetCoord.toString());
                game.inGameScreen.hexPath = HexHelper.hexagonLineDraw(game.inGameScreen.t_hexOne.offsetCoord,
                        game.inGameScreen.t_hexTwo.offsetCoord);

            }
        }
        else {
            game.inGameScreen.t_hexZero = new Hexagon(new OffsetCoord(0,0));
        }

        /*switch (GameManager.getInstance().game.inGameScreen.currentAction) {
            case MOVE:
                for(Entity entity : game.inGameScreen.engine.getEntitiesFor(Family.all(TypeComponent.class).get())) {
                    //If the entity is the player and they are not already moving
                    if(entity.getComponent(TypeComponent.class).type == TypeComponent.PLAYER &&
                            !entity.getComponent(MoveComponent.class).isMoving) {
                        entity.getComponent(MoveComponent.class).nextPosition = new Hexagon(HexHelper.getCoordinatesFromHexCenter(actor.position));
                        entity.getComponent(MoveComponent.class).shouldMove = true;
                    }
                }
                break;
            default:
                break;
        }*/
    }
}
