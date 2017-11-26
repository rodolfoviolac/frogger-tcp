package com.frogger.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FroggerGame extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite player;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Sprite(new Texture("frog.jpg"));


	}

	@Override
	public void render () {

		if(Gdx.input.isKeyPressed(Input.Keys.W)){
		player.setY(player.getY() + 10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
		player.setY(player.getY() - 10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
		player.setX(player.getX() - 10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
		player.setX(player.getX() + 10);
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(player, player.getX(), player.getY());
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		player.getTexture().dispose();
	}
}