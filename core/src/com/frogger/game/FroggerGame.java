package com.frogger.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frogger.game.screens.MainGameScreen;


public class FroggerGame extends Game {
    public static final int V_WIDTH = 624;
    public static final int V_HEIGHT = 720;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainGameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}