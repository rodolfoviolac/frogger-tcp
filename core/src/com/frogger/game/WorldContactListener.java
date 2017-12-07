package com.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.frogger.game.screens.MainGameScreen;
import com.frogger.game.sprites.Frog;

public class WorldContactListener implements ContactListener {
    private Frog frog;

    public WorldContactListener(Frog frog){
        this.frog = frog;
    }

    @Override
    public void beginContact(Contact contact) {
        frog.die(true);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
