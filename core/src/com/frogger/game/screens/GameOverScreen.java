package com.frogger.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.FroggerGame;
import com.frogger.game.Player;
import com.frogger.game.Sounds;
import com.frogger.game.fileHandler.WriteJson;


public class GameOverScreen implements Screen {

    private Texture gameOverLogo;
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private BitmapFont scoreFont;
    private int score;
    private FroggerGame game;

    private static final int gameOverLogoWidth = 300;
    private static final int gameOverLogoHeight = 120;
    private static final int gameOverLogoY = 500;
    private static final int backButtonWidth = 180;
    private static final int backButtonHeight = 70;
    private static final int backButtonY = 50;


    public GameOverScreen(final FroggerGame game, Player player){
        this.game = game;

        gameOverLogo = new Texture("menu-assets/logo-gameOver.png");
        backButtonActive = new Texture("menu-assets/backButton1.png");
        backButtonInactive = new Texture("menu-assets/backButton2.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        score = player.getScore();

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(backButtonIsHover()){
                    Sounds.menuSound();
                    game.setScreen(new MainMenuScreen(game));
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        Sounds.menuLoopStop();
        Gdx.input.getTextInput(textListener, "Nome para High Score: ", "Seu nome", "");
    }

    private boolean backButtonIsHover(){
        if(Gdx.input.getX() < backPositionX() + backButtonWidth && Gdx.input.getX() > backPositionX() && FroggerGame.screenHeight - Gdx.input.getY() < backButtonY + backButtonHeight && FroggerGame.screenHeight - Gdx.input.getY() > backButtonY){
            return true;
        } else return false;
    }

    private int backPositionX(){
        return (FroggerGame.screenWidth / 2) - backButtonWidth / 2;
    }

    private int gameOverLogoPositionX(){
        return (FroggerGame.screenWidth / 2) - gameOverLogoWidth / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        if(backButtonIsHover()){
            game.batch.draw(backButtonActive, backPositionX(), backButtonY, backButtonWidth, backButtonHeight);
        } else {
            game.batch.draw(backButtonInactive, backPositionX(), backButtonY, backButtonWidth, backButtonHeight);
        }

        game.batch.draw(gameOverLogo, gameOverLogoPositionX(), gameOverLogoY, gameOverLogoWidth , gameOverLogoHeight);


        GlyphLayout seuScoreText = new GlyphLayout(scoreFont,  " Seu Score: ", Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, seuScoreText, FroggerGame.screenWidth / 2 - 190, 450);

        GlyphLayout scoreNumberText = new GlyphLayout(scoreFont,  String.valueOf(score), Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, scoreNumberText, FroggerGame.screenWidth / 2 - 50, 350);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    Input.TextInputListener textListener = new Input.TextInputListener()
    {
        @Override
        public void input(String input)
        {
            System.out.println(input);

            WriteJson.addPlayerToDB(input,score);
        }

        @Override
        public void canceled()
        {
            System.out.println("Aborted");
        }
    };

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
