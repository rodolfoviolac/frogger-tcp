package com.frogger.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.FroggerGame;
import com.frogger.game.Player;
import com.frogger.game.StageGame;
import com.frogger.game.WorldContactListener;
import com.frogger.game.scenes.Hud;
import com.frogger.game.sprites.Frog;
import com.frogger.game.sprites.Vehicle;


public class MainGameScreen implements Screen {
    FroggerGame game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private StageGame stageGame;
    private Player player;
    private int currentLevel;


    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Frog frog;
    //private Vehicle vehicle;

    public MainGameScreen(FroggerGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FroggerGame.V_WIDTH,FroggerGame.V_HEIGHT,gamecam);
        //hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("bground.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        /*BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Boy body;*/

        player = new Player(game.batch);
        currentLevel = 1;
        stageGame = new StageGame(world, game.batch, currentLevel, this);
        frog = new Frog(world, 3, game.batch, this);

        world.setContactListener(new WorldContactListener(frog));

    }

    public void nextStage(){
        int stageTimer = stageGame.getStageTimer();
        int stageLevel = stageGame.getLevel();
        player.setScore(stageTimer, stageLevel);
        stageGame.dispose();
        int nextLevel = currentLevel + 1;
        currentLevel = nextLevel;
        stageGame = new StageGame(world, game.batch, nextLevel, this);
    }

    public void gameOver(){
        game.setScreen(new GameOverScreen(game));
    }


    @Override
    public void show() {
    }

    public void handleInput(float dt) {
    }

    public void update(float dt){

       // vehicle.update(vehicle);
        stageGame.update(dt);
        frog.handlePositionOfFrog(frog);

        world.step(1/60f, 6, 2);

        gamecam.update();
        renderer.setView(gamecam);

        if (frog.lives == 0 || stageGame.getStageTimer() == 0){
            gameOver();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(frog.getStage().getCamera().combined);

        frog.hudFrog();
        frog.getStage().draw();

        player.hudPlayer();
        player.getStage().draw();

        stageGame.hudStage();
        stageGame.getStage().draw();



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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        frog.dispose();
        stageGame.dispose();
    }
}
