package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.frogger.game.FroggerGame;
import com.frogger.game.Sounds;

public class MainMenuScreen implements Screen {

    private FroggerGame game;

    private final int BUTTON_WIDTH = 190;
    private final int BUTTON_HEIGHT = 90;

    private final int FROGGER_LOGO_WIDTH = 500;
    private final int FROGGER_LOGO_HEIGHT = 150;

    private final int PLAY_BUTTON_Y = 340;
    private final int SCORE_BUTTON_Y = 210;
    private final int EXIT_BUTTON_Y = 75;
    private final int FROGGER_LOGO_Y = 500;

    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture scoreButtonActive;
    private Texture scoreButtonInactive;
    private Texture froggerLogo;

    public MainMenuScreen(final FroggerGame game) {
        this.game = game;
        defineTextureButtons();
        touchControl();
    }

    private void defineTextureButtons(){
        playButtonActive = new Texture("menu-assets/playButton1.png");
        playButtonInactive = new Texture("menu-assets/playButton2.png");
        scoreButtonActive = new Texture("menu-assets/scoreButton1.png");
        scoreButtonInactive = new Texture("menu-assets/scoreButton2.png");
        exitButtonActive = new Texture("menu-assets/exitButton1.png");
        exitButtonInactive = new Texture("menu-assets/exitButton2.png");
        froggerLogo = new Texture("menu-assets/logo-frogger.png");
    }

    private void touchControl(){
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
        if (Gdx.input.getX() < buttonPositionX() + BUTTON_WIDTH && Gdx.input.getX() > buttonPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTON_HEIGHT && FroggerGame.screenHeight - Gdx.input.getY() > EXIT_BUTTON_Y) {
            return true;
        } else return false;
    }

    private boolean scoreButtonIsHover() {
        if (Gdx.input.getX() < buttonPositionX() + BUTTON_WIDTH && Gdx.input.getX() > buttonPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < SCORE_BUTTON_Y + BUTTON_HEIGHT && FroggerGame.screenHeight - Gdx.input.getY() > SCORE_BUTTON_Y) {
            return true;
        } else return false;
    }

    private boolean playButtonIsHover() {
        if (Gdx.input.getX() < buttonPositionX() + BUTTON_WIDTH && Gdx.input.getX() > buttonPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTON_HEIGHT && FroggerGame.screenHeight - Gdx.input.getY() > PLAY_BUTTON_Y) {
            return true;
        } else return false;
    }

    private int buttonPositionX() {
        return (FroggerGame.screenWidth / 2) - BUTTON_WIDTH / 2;
    }

    private int froggerLogoPositionX() {
        return (FroggerGame.screenWidth / 2) - FROGGER_LOGO_WIDTH / 2;
    }

    private void logoFroggerDraw(){
        game.batch.draw(froggerLogo, froggerLogoPositionX(), FROGGER_LOGO_Y, FROGGER_LOGO_WIDTH, FROGGER_LOGO_HEIGHT);
    }

    private void buttonsDraw(){
        if (exitButtonIsHover()) {
            game.batch.draw(exitButtonActive, buttonPositionX(), EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, buttonPositionX(), EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if (scoreButtonIsHover()) {
            game.batch.draw(scoreButtonActive, buttonPositionX(), SCORE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        } else {
            game.batch.draw(scoreButtonInactive, buttonPositionX(), SCORE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if (playButtonIsHover()) {
            game.batch.draw(playButtonActive, buttonPositionX(), PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        } else {
            game.batch.draw(playButtonInactive, buttonPositionX(), PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        logoFroggerDraw();
        buttonsDraw();
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
