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
        map = maploader.load("stage.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        frog = new Frog(world, 3);
        frog.hudFrog(game.batch);
        player = new Player(game.batch);
        frog.screen = this;

        //vehicle = new Vehicle(world,"car", 2, new Vector2(100, 100));

        stageGame = new StageGame(world, 2);

        world.setContactListener(new WorldContactListener(frog));

    }

    public void nextStage(){
        stageGame.dispose();
        stageGame = new StageGame(world, 5);
    }


    @Override
    public void show() {
    }

    public void handleInput(float dt) {
    }

    public void update(float dt){
        frog.handleInput(dt, frog);
       // vehicle.update(vehicle);
        stageGame.update();

        world.step(1/60f, 6, 2);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(frog.stage.getCamera().combined);
        //hud.stage.draw();
        frog.hudFrog(game.batch);
        frog.stage.draw();
        player = new Player(game.batch);
        player.stage.draw();
        stageGame.hudStage(game.batch);
        stageGame.stage.draw();

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
       // stageGame.dispose();
    }
}