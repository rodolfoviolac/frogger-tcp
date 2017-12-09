package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.frogger.game.FroggerGame;
import com.frogger.game.fileHandler.ReadJson;
import com.frogger.game.screens.MainMenuScreen;
import com.google.gson.Gson;
import com.oracle.javafx.jmx.json.JSONReader;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class HighScoreMenuScreen implements Screen {

    FroggerGame game;
    BitmapFont scoreFont;
    Texture backButtonActive;
    Texture backButtonInactive;
    Texture froggerLogo;

    private static final int backButtonWidth = 180;
    private static final int backButtonHeight = 70;
    private static final int froggerLogoWidth = 300;
    private static final int froggerLogoHeight = 120;
    private static final int backButtonY = 50;
    private static final int froggerLogoY = 580;

    final HighScoreMenuScreen HighScoreMenuScreen = this;



    final String[] listOf10BestPlayer = ReadJson.get10BestPlayers();
    final int[] listof10BestScores = ReadJson.get10BestScores();

    public HighScoreMenuScreen (final FroggerGame game) {
        this.game = game;

        backButtonActive = new Texture("menu-assets/backButton1.png");
        backButtonInactive = new Texture("menu-assets/backButton2.png");
        froggerLogo = new Texture("menu-assets/highScore-logo.png");

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                if(backButtonIsHover()){
                    HighScoreMenuScreen.dispose();
                    game.setScreen(new MainMenuScreen(game));
                }


                return super.touchUp(screenX, screenY, pointer, button);
            }
        });


    }

    private boolean backButtonIsHover(){
        if(Gdx.input.getX() < backPositionX() + backButtonWidth && Gdx.input.getX() > backPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < backButtonY + backButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > backButtonY){
            return true;
        } else return false;
    }


    private int backPositionX(){
        return (FroggerGame.screenWidth / 2) - backButtonWidth / 2;
    }


    private int froggerLogoPositionX(){
        return (FroggerGame.screenWidth / 2) - froggerLogoWidth / 2;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(froggerLogo, froggerLogoPositionX(), froggerLogoY, froggerLogoWidth ,froggerLogoHeight);

        if(backButtonIsHover()){
            game.batch.draw(backButtonActive, backPositionX(), backButtonY, backButtonWidth, backButtonHeight);
        } else {
            game.batch.draw(backButtonInactive, backPositionX(), backButtonY, backButtonWidth, backButtonHeight);
        }

        GlyphLayout[] arr = new GlyphLayout[10];

        for (int i=0; i <= 9; i++){
            if(listOf10BestPlayer[i] != null) {
                arr[i] = new GlyphLayout(scoreFont, listOf10BestPlayer[i] + " ..." + listof10BestScores[i], Color.WHITE, 0, Align.left, false);
                scoreFont.draw(game.batch, arr[i], FroggerGame.screenWidth / 2 - 250, 550 - (i * 40));
            }
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
