package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.*;
import com.frogger.game.sprites.Frog;


public class MainGameScreen implements Screen {
    private FroggerGame game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private StageGame stageGame;
    private Player player;
    private int currentLevel;
    private final float TIME_STEP = 1/60f;

    //Tiled map variables
    private TmxMapLoader bgLoader;
    private TiledMap background;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;

    private Frog frog;

    public MainGameScreen(FroggerGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FroggerGame.screenWidth,FroggerGame.screenHeight,gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);

        bgLoader = new TmxMapLoader();
        background = bgLoader.load("bground.tmx");
        renderer = new OrthogonalTiledMapRenderer(background);

        world = new World(new Vector2(0,0), true);

        player = new Player(game.batch);
        currentLevel = 1;
        stageGame = new StageGame(world, game.batch, currentLevel);
        frog = new Frog(world, game.batch, this);

        world.setContactListener(new WorldContactListener(frog));
        Sounds.menuLoopStart();
    }

    public void nextStage(){
        int stageTimer = stageGame.getStageTimer();
        int stageLevel = stageGame.getLevel();
        player.setScore(stageTimer, stageLevel);
        stageGame.dispose(world);
        int nextLevel = currentLevel + 1;
        currentLevel = nextLevel;
        stageGame = new StageGame(world, game.batch, nextLevel);
    }

    private void gameOver(){
        game.setScreen(new GameOverScreen(game, player));
    }


    @Override
    public void show() {
    }

    private void update(float dt){
        world.step(TIME_STEP, 6, 2);

        stageGame.update(dt);
        frog.handlePositionOfFrog(frog);

        gamecam.update();
        renderer.setView(gamecam);

        frog.update(dt);

        if (frog.getLives() == 0 || stageGame.getStageTimer() == 0){
            gameOver();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.begin();
        frog.drawFrog(game.batch);
        stageGame.draw(game.batch);
        game.batch.end();

        printHud();
    }

    private void printHud(){
        frog.hudLives();
        player.hudScore();
        stageGame.hudTime();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        background.dispose();
        renderer.dispose();
        world.dispose();
        frog.dispose();
        stageGame.dispose(world);
    }
}
