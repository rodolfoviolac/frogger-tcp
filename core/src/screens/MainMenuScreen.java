package screens;

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

    private static final int exitButtonWidth = 300;
    private static final int exitButtonHeight = 150;
    private static final int playButtonWidth = 300;
    private static final int playButtonHeight = 150;
    private static final int playButtonY = 280;
    private static final int exitButtonY = 100;


    public MainMenuScreen (FroggerGame game) {
    this.game = game;
    playButtonActive = new Texture("assets-raw/images/play_button_active.png");
    playButtonInactive = new Texture("assets-raw/images/play_button_inactive.png");
    exitButtonActive = new Texture("assets-raw/images/exit_button_active.png");
    exitButtonInactive = new Texture("assets-raw/images/exit_button_inactive.png");
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        if(exitButtonIsHover()){
            game.batch.draw(exitButtonActive, exitPositionX(), exitButtonY, exitButtonWidth, exitButtonHeight);
        } else {
            game.batch.draw(exitButtonInactive, exitPositionX(), exitButtonY, exitButtonWidth, exitButtonHeight);
        }

        if(playButtonIsHover()){
            game.batch.draw(playButtonActive, playPositionX(), playButtonY, playButtonWidth, playButtonHeight);
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
