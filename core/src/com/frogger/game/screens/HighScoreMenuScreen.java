package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.frogger.game.FroggerGame;
import com.frogger.game.Sounds;
import com.frogger.game.fileHandler.ReadJson;



public class HighScoreMenuScreen implements Screen {
    private FroggerGame game;
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private Texture highScoreLogo;
    private BitmapFont scoreFont;
    private GlyphLayout[] arr;

    private final int BACK_BUTTON_Y = 50;
    private final int HIGHSCORE_LOGO_Y = 580;
    private final int NUM_OF_BEST_SCORES = 10;

    final HighScoreMenuScreen HighScoreMenuScreen = this;

    final String[] listOf10BestPlayer = ReadJson.get10BestPlayers();
    final int[] listOf10BestScores = ReadJson.get10BestScores();

    public HighScoreMenuScreen (final FroggerGame game) {
        this.game = game;
        arr = new GlyphLayout[NUM_OF_BEST_SCORES];
        defineTextures();
        setTextFont();
        touchControl();
    }

    private void defineTextures(){
        backButtonActive = new Texture("menu-assets/backButton1.png");
        backButtonInactive = new Texture("menu-assets/backButton2.png");
        highScoreLogo = new Texture("menu-assets/highScore-logo.png");
    }

    private void setTextFont(){
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    private void touchControl(){
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(backButtonIsHover()){
                    HighScoreMenuScreen.dispose();
                    Sounds.menuSound();
                    game.setScreen(new MainMenuScreen(game));
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    private boolean backButtonIsHover(){
        if(Gdx.input.getX() < backPositionX() + backButtonActive.getWidth()
                && Gdx.input.getX() > backPositionX()
                && Gdx.graphics.getHeight() - Gdx.input.getY() < BACK_BUTTON_Y + backButtonActive.getHeight()
                && Gdx.graphics.getHeight() - Gdx.input.getY() > BACK_BUTTON_Y){
            return true;
        } else return false;
    }

    private int backPositionX(){
        return (Gdx.graphics.getWidth() / 2) - backButtonActive.getWidth() / 2;
    }

    private int highScoreLogoPositionX(){
        return (Gdx.graphics.getWidth() / 2) - highScoreLogo.getWidth() / 2;
    }

    private void backButtonDraw(){
        if(backButtonIsHover()){
            game.batch.draw(backButtonActive, backPositionX(), BACK_BUTTON_Y);
        } else {
            game.batch.draw(backButtonInactive, backPositionX(), BACK_BUTTON_Y);
        }
    }

    private void printScores(){
        for (int i=0; i <= NUM_OF_BEST_SCORES - 1; i++){
            if(listOf10BestPlayer[i] != null) {
                arr[i] = new GlyphLayout(scoreFont, listOf10BestPlayer[i] + " ..." + listOf10BestScores[i], Color.WHITE, 0, Align.left, false);
                scoreFont.draw(game.batch, arr[i], Gdx.graphics.getWidth() / 2 - 250, 550 - (i * 40));
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(highScoreLogo, highScoreLogoPositionX(), HIGHSCORE_LOGO_Y);
        backButtonDraw();
        printScores();
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
