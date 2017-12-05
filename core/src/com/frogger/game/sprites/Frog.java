package com.frogger.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.FroggerGame;
import com.frogger.game.screens.MainGameScreen;

public class Frog extends Sprite implements Disposable {
    public World world;
    public Body b2body;
    public MainGameScreen screen;

    private boolean frogDied;
    public Stage stage;
    private Viewport viewport;

    private final Vector2 positionInitial = new Vector2(Gdx.graphics.getWidth() / 2,120);
    private final Integer JUMP_SIZE = 48;
    private final Integer SIZE_OF_FROG = 22;

    private float timeCount;
    private Integer score;
    private Integer lives;
    private Integer worldTimer;

    Label scoreCountLabel;
    Label scoreLabel;
    Label timeCountLabel;
    Label timeLabel;
    Label livesCountLabel;
    Label livesLabel;

    public Frog(World world, int lives){
        this.world = world;
        this.lives = lives;
        defineFrog();
    }

    public void defineFrog(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(positionInitial);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SIZE_OF_FROG,SIZE_OF_FROG);

        fdef.shape = shape;
        //fdef.isSensor = true;
        b2body.createFixture(fdef);
    }

    public void handleInput(float dt, Frog frog){
        Vector2 currentPosition = new Vector2(frog.b2body.getPosition());
        if(Gdx.input.isKeyJustPressed((Input.Keys.UP))){
            if(currentPosition.y < Gdx.graphics.getHeight() - SIZE_OF_FROG) {
                frog.b2body.setTransform(currentPosition.add(0, JUMP_SIZE), 0);
            }
        } else if(Gdx.input.isKeyJustPressed((Input.Keys.DOWN))){
            if(currentPosition.y > SIZE_OF_FROG) {
                frog.b2body.setTransform(currentPosition.add(0, -JUMP_SIZE), 0);
            }
        } else if(Gdx.input.isKeyJustPressed((Input.Keys.LEFT))){
            if(currentPosition.x > SIZE_OF_FROG) {
                frog.b2body.setTransform(currentPosition.add(-JUMP_SIZE, 0), 0);
            }
        } else if(Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))){
            if(currentPosition.x < Gdx.graphics.getWidth() - SIZE_OF_FROG) {
                frog.b2body.setTransform(currentPosition.add(JUMP_SIZE, 0), 0);
            }
        } else if(frogDied){
            frog.b2body.setTransform(positionInitial, 0);
            frogDied = false;
        } else if(Gdx.input.isKeyJustPressed((Input.Keys.W))) screen.nextStage();


    }


    public void hudFrog(SpriteBatch sb){
        timeCount = 0;
        score = 0;
        worldTimer = 300;

        viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        livesLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesCountLabel = new Label(String.format("%01d",lives),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        livesLabel.setFontScale(2);
        livesCountLabel.setFontScale(2);

        table.add().expandX();
        table.add(livesLabel).expandX();
        table.add().expandX();
        table.row();
        table.add().expandX().padBottom(10);
        table.add(livesCountLabel).expandX().padBottom(10);
        table.add().expandX().padBottom(10);

        stage.addActor(table);

    }

    public void die(boolean frogDied) {
        this.frogDied = frogDied;
        lives = lives - 1;
        Gdx.app.log("Morreu", "...");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
