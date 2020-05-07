package com.ds.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.ds.game.Entities.Player;

public class WorldContactListener implements ContactListener {

    private Player player;

    private boolean isNonWallInteractionActive;

    public WorldContactListener(Player player){
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        if(!contact.getFixtureA().getBody().getUserData().toString().equals("wall")){
            isNonWallInteractionActive = true;
            player.currentInteraction = contact.getFixtureA().getBody().getUserData().toString();
        }
        else if(!isNonWallInteractionActive && contact.getFixtureA().getBody().getUserData().equals("wall")){
            player.currentInteraction = "wall";
        }
//        Gdx.app.log(contact.getFixtureA().getBody().getUserData().toString(), contact.getFixtureB().getBody().getUserData().toString());

    }

    @Override
    public void endContact(Contact contact) {
        if(!contact.getFixtureA().getBody().getUserData().equals("wall")){
            isNonWallInteractionActive = false;
        }
        player.currentInteraction = "";
//        Gdx.app.log("End: " + contact.getFixtureA().getBody().getUserData().toString(), contact.getFixtureB().getBody().getUserData().toString());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
