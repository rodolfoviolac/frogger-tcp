package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.frogger.game.FroggerGame;


public class MainGameScreen implements Screen {
    public static final float froggerSpeed = 30;
    Sprite player;

    FroggerGame game;

    public MainGameScreen(FroggerGame game){
        this.game = game;

    }


    @Override
    public void show() {
        player = new Sprite(new Texture("frog.jpg"));
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) ||  Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.setY(player.getY() + (froggerSpeed + Gdx.graphics.getDeltaTime()));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            player.setY(player.getY() - (froggerSpeed + Gdx.graphics.getDeltaTime()));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            player.setX(player.getX() - (froggerSpeed + Gdx.graphics.getDeltaTime()));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.D )){
            player.setX(player.getX() + (froggerSpeed + Gdx.graphics.getDeltaTime()));
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(player, player.getX(), player.getY());
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
