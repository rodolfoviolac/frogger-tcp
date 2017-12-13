package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.frogger.game.FroggerGame;
import com.frogger.game.Sounds;

public class MainMenuScreen implements Screen {

    private FroggerGame game;
    
    private final int menuButtonWidth = 190;
    private final int menuButtonHeight = 90;

    private static final int froggerLogoWidth = 500;
    private static final int froggerLogoHeight = 150;
    private static final int playButtonY = 340;
    private static final int scoreButtonY = 210;
    private static final int exitButtonY = 75;
    private static final int froggerLogoY = 500;

    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture scoreButtonActive;
    private Texture scoreButtonInactive;
    private Texture froggerLogo;




    public MainMenuScreen(final FroggerGame game) {
        this.game = game;
        playButtonActive = new Texture("menu-assets/playButton1.png");
        playButtonInactive = new Texture("menu-assets/playButton2.png");
        scoreButtonActive = new Texture("menu-assets/scoreButton1.png");
        scoreButtonInactive = new Texture("menu-assets/scoreButton2.png");
        exitButtonActive = new Texture("menu-assets/exitButton1.png");
        exitButtonInactive = new Texture("menu-assets/exitButton2.png");
        froggerLogo = new Texture("menu-assets/logo-frogger.png");


        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (scoreButtonIsHover()) {
                    Sounds.menuSound();
                    game.setScreen(new HighScoreMenuScreen(game));
                } else if (playButtonIsHover()) {
                    Sounds.menuSound();
                    game.setScreen(new MainGameScreen(game));
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });


    }

    private boolean exitButtonIsHover() {
        if (Gdx.input.getX() < exitPositionX() + menuButtonWidth && Gdx.input.getX() > exitPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < exitButtonY + menuButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > exitButtonY) {
            return true;
        } else return false;
    }

    private boolean scoreButtonIsHover() {
        if (Gdx.input.getX() < scorePositionX() + menuButtonWidth && Gdx.input.getX() > scorePositionX() && FroggerGame.screenHeight - Gdx.input.getY() < scoreButtonY + menuButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > scoreButtonY) {
            return true;
        } else return false;
    }

    private boolean playButtonIsHover() {
        if (Gdx.input.getX() < playPositionX() + menuButtonWidth && Gdx.input.getX() > playPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < playButtonY + menuButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > playButtonY) {
            return true;
        } else return false;
    }

    private int exitPositionX() {
        return (FroggerGame.screenWidth / 2) - menuButtonWidth / 2;
    }

    private int playPositionX() {
        return (FroggerGame.screenWidth / 2) - menuButtonWidth / 2;
    }

    private int scorePositionX() {
        return (FroggerGame.screenWidth / 2) - menuButtonWidth / 2;
    }

    private int froggerLogoPositionX() {
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

        game.batch.draw(froggerLogo, froggerLogoPositionX(), froggerLogoY, froggerLogoWidth, froggerLogoHeight);

        if (exitButtonIsHover()) {
            game.batch.draw(exitButtonActive, exitPositionX(), exitButtonY, menuButtonWidth, menuButtonHeight);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, exitPositionX(), exitButtonY, menuButtonWidth, menuButtonHeight);
        }

        if (scoreButtonIsHover()) {
            game.batch.draw(scoreButtonActive, scorePositionX(), scoreButtonY, menuButtonWidth, menuButtonHeight);
        } else {
            game.batch.draw(scoreButtonInactive, scorePositionX(), scoreButtonY, menuButtonWidth, menuButtonHeight);
        }

        if (playButtonIsHover()) {
            game.batch.draw(playButtonActive, playPositionX(), playButtonY, menuButtonWidth, menuButtonHeight);
        } else {
            game.batch.draw(playButtonInactive, playPositionX(), playButtonY, menuButtonWidth, menuButtonHeight);
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
