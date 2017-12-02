package com.frogger.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frogger.game.screens.MainMenuScreen;


public class FroggerGame extends Game {
	public SpriteBatch batch;

	public static final int screenWidth = 1000;
	public static final int screenHeight = 700;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));


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