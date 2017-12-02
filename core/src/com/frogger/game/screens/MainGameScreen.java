package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.frogger.game.FroggerGame;



public class MainGameScreen implements Screen {
    public static final int froggerWidth = 60;
    public static final int froggerHeight = 75;
    public static final float froggerSpeed = 30;
    public static final float froggerAnimationSpeed = 0.4f;
    public static final float rollTimerSwitchTime = 0.015f;

    Animation[] rolls;

    int playerX = 0;
    int playerY = 0;
    int roll;
    float stateTime;
    float rollTimer;

    Sprite player;

    FroggerGame game;

    public MainGameScreen(FroggerGame game){
        this.game = game;
        playerY = 0;
        playerX = FroggerGame.screenWidth/2 - froggerWidth / 2;

        roll = 3;
        rollTimer=0;
        rolls = new Animation[16];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("assets-raw/images/captainamerica_shield.png"), 32, 48 );

        rolls[0] = new Animation(froggerAnimationSpeed, rollSpriteSheet[0]);
        rolls[1] = new Animation(froggerAnimationSpeed, rollSpriteSheet[1]);
        rolls[2] = new Animation(froggerAnimationSpeed, rollSpriteSheet[2]);
        rolls[3] = new Animation(froggerAnimationSpeed, rollSpriteSheet[3]);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) ||  Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            playerY += froggerSpeed + Gdx.graphics.getDeltaTime();

            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > rollTimerSwitchTime){
                rollTimer = 0;
                roll = 3;
            }

        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.S) ||  Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            playerY -= froggerSpeed + Gdx.graphics.getDeltaTime();
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > rollTimerSwitchTime){
                rollTimer = 0;
                roll = 0;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A) ||  Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            playerX -= froggerSpeed + Gdx.graphics.getDeltaTime();
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > rollTimerSwitchTime){
                rollTimer = 0;
                roll = 1;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.D) ||  Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            playerX += froggerSpeed + Gdx.graphics.getDeltaTime();
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > rollTimerSwitchTime){
                rollTimer = 0;
                roll = 2;
            }
        }

        stateTime += delta;

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame( stateTime,true), playerX, playerY, froggerWidth, froggerHeight);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
