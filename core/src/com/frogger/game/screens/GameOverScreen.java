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
    private FroggerGame game;

    private Texture gameOverLogo;
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private BitmapFont scoreFont;

    private int score;
    private final int GAME_OVER_LOGO_Y = 500;
    private final int BACK_BUTTON_Y = 50;


    public GameOverScreen(final FroggerGame game, Player player){
        this.game = game;

        defineTextures();
        defineFont();
        touchControl();

        Sounds.menuLoopStop();
        score = player.getScore();
        Gdx.input.getTextInput(textListener, "Name to High Score: ", "Your name", "");
    }

    private void defineTextures(){
        gameOverLogo = new Texture("menu-assets/logo-gameOver.png");
        backButtonActive = new Texture("menu-assets/backButton1.png");
        backButtonInactive = new Texture("menu-assets/backButton2.png");
    }

    private void defineFont(){
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    private void touchControl(){
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
    }

    private boolean backButtonIsHover(){
        if(Gdx.input.getX() < backPositionX() + backButtonActive.getWidth() && Gdx.input.getX() > backPositionX() && Gdx.graphics.getHeight() - Gdx.input.getY() < BACK_BUTTON_Y + backButtonActive.getHeight() && Gdx.graphics.getHeight() - Gdx.input.getY() > BACK_BUTTON_Y){
            return true;
        } else return false;
    }

    private int backPositionX(){
        return (Gdx.graphics.getWidth() / 2) - backButtonActive.getWidth() / 2;
    }

    private int gameOverLogoPositionX(){
        return (Gdx.graphics.getWidth() / 2) - gameOverLogo.getWidth() / 2;
    }

    private void backButtonDraw(){
        if(backButtonIsHover()){
            game.batch.draw(backButtonActive, backPositionX(), BACK_BUTTON_Y);
        } else {
            game.batch.draw(backButtonInactive, backPositionX(), BACK_BUTTON_Y);
        }
    }

    private void printScore(){
        GlyphLayout seuScoreText = new GlyphLayout(scoreFont,  " Your Score: ", Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, seuScoreText, Gdx.graphics.getWidth() / 2 - 190, 450);

        GlyphLayout scoreNumberText = new GlyphLayout(scoreFont,  String.valueOf(score), Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, scoreNumberText, Gdx.graphics.getWidth() / 2 - 50, 350);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(gameOverLogo, gameOverLogoPositionX(), GAME_OVER_LOGO_Y);
        backButtonDraw();
        printScore();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    Input.TextInputListener textListener = new Input.TextInputListener()
    {
        @Override
        public void input(String input) {
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
