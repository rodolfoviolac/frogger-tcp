package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.frogger.game.FroggerGame;

public class MainMenuScreen implements Screen {

    FroggerGame game;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture froggerLogo;

    private static final int exitButtonWidth = 300;
    private static final int exitButtonHeight = 150;
    private static final int playButtonWidth = 300;
    private static final int playButtonHeight = 150;
    private static final int froggerLogoWidth = 500;
    private static final int froggerLogoHeight = 150;
    private static final int playButtonY = 240;
    private static final int exitButtonY = 90;
    private static final int froggerLogoY = 450;


    public MainMenuScreen (FroggerGame game) {
    this.game = game;
    playButtonActive = new Texture("assets-raw/images/play_button_active.png");
    playButtonInactive = new Texture("assets-raw/images/play_button_inactive.png");
    exitButtonActive = new Texture("assets-raw/images/exit_button_active.png");
    exitButtonInactive = new Texture("assets-raw/images/exit_button_inactive.png");
    froggerLogo = new Texture("assets-raw/images/logo.png");
    }

    private boolean exitButtonIsHover(){
        if(Gdx.input.getX() < exitPositionX() + exitButtonWidth && Gdx.input.getX() > exitPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < exitButtonY + exitButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > exitButtonY){
        return true;
        } else return false;
    }

    private boolean playButtonIsHover(){
        if(Gdx.input.getX() < playPositionX() + playButtonWidth && Gdx.input.getX() > playPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < playButtonY + playButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > playButtonY){
            return true;
        } else return false;
    }

    private int exitPositionX(){
        return (FroggerGame.screenWidth / 2) - exitButtonWidth / 2;
    }

    private int playPositionX(){
        return (FroggerGame.screenWidth / 2) - playButtonWidth / 2;
    }

    private int froggerLogoPositionX(){
        return (FroggerGame.screenWidth / 2) - froggerLogoWidth / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(froggerLogo, froggerLogoPositionX(), froggerLogoY, froggerLogoWidth ,froggerLogoHeight);

        if(exitButtonIsHover()){
            game.batch.draw(exitButtonActive, exitPositionX(), exitButtonY, exitButtonWidth, exitButtonHeight);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, exitPositionX(), exitButtonY, exitButtonWidth, exitButtonHeight);
        }

        if(playButtonIsHover()){
            game.batch.draw(playButtonActive, playPositionX(), playButtonY, playButtonWidth, playButtonHeight);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, playPositionX(), playButtonY, playButtonWidth, playButtonHeight);
        }


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
