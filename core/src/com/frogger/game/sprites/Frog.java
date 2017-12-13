package com.frogger.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.frogger.game.Sounds;
import com.frogger.game.screens.MainGameScreen;

public class Frog extends Sprite implements Disposable {
    private World world;
    private Body b2body;
    private MainGameScreen screen;
    private Texture imgFrog;

    private boolean frogDied;
    private Viewport viewport;
    private Stage stage;
    private final Vector2 positionInitial;

    private final int NUM_OF_LIVES = 3;
    private final int COORD_Y_FROG_INITIAL = 120;
    private final Integer JUMP_SIZE = 48;
    private final Integer RADIUS_OF_FROG = 22; //ANTIGO SIZE_OF_FROG
    private final Integer SIZE_BOX_FROG = 48;

    private Integer lives;
    private Label livesCountLabel;
    private Label livesLabel;


    public Frog(World world, SpriteBatch sb, MainGameScreen screen){
        this.screen = screen;
        this.world = world;
        lives = NUM_OF_LIVES;
        viewport = new FitViewport(FroggerGame.screenWidth, FroggerGame.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        positionInitial = new Vector2(Gdx.graphics.getWidth() / 2,COORD_Y_FROG_INITIAL);
        livesCountLabel = new Label(String.format("%01d",lives),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        defineFrogBox();
        imgFrog = new Texture("frog/frog_up.png");
    }

    private void defineFrogBox(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(positionInitial);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RADIUS_OF_FROG,RADIUS_OF_FROG);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public int getLives(){
        return lives;
    }

    public void drawFrog(SpriteBatch sb){
        sb.draw(imgFrog,b2body.getPosition().x - SIZE_BOX_FROG / 2,b2body.getPosition().y - SIZE_BOX_FROG / 2);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - RADIUS_OF_FROG, b2body.getPosition().y - RADIUS_OF_FROG);
    }

    public void handlePositionOfFrog(Frog frog) {
        Vector2 currentPosition = new Vector2(frog.b2body.getPosition());
        if (Gdx.input.isKeyJustPressed((Input.Keys.UP))) {
            goUp(frog, currentPosition);
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.DOWN))) {
            goDown(frog, currentPosition);
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.LEFT))) {
            goLeft(frog, currentPosition);
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))) {
           goRight(frog, currentPosition);
        } else if(frogDied) {
            frog.b2body.setTransform(positionInitial, 0);
            frogDied = false;
            Sounds.frogDie();
        }
    }

    private void goUp(Frog frog, Vector2 currentPosition){
        Sounds.frogJump();
        frog.b2body.setTransform(currentPosition.add(0, JUMP_SIZE), 0);
        imgFrog = new Texture("frog/frog_up.png");
        if (currentPosition.y >= Gdx.graphics.getHeight()) {
            Sounds.nextLevel();
            frog.b2body.setTransform(positionInitial, 0);
            screen.nextStage();
        }
    }

    private void goDown(Frog frog, Vector2 currentPosition){
        if (currentPosition.y > (SIZE_BOX_FROG / 2) + 2*JUMP_SIZE) {
            Sounds.frogJump();
            frog.b2body.setTransform(currentPosition.add(0, -JUMP_SIZE), 0);
            imgFrog = new Texture("frog/frog_down.png");
        }
    }

    private void goLeft(Frog frog, Vector2 currentPosition){
        if (currentPosition.x > (SIZE_BOX_FROG / 2)) {
            Sounds.frogJump();
            frog.b2body.setTransform(currentPosition.add(-JUMP_SIZE, 0), 0);
            imgFrog = new Texture("frog/frog_left.png");
        }
    }

    private void goRight(Frog frog, Vector2 currentPosition){
        if (currentPosition.x < Gdx.graphics.getWidth() - (SIZE_BOX_FROG / 2) - 4) {
            Sounds.frogJump();
            frog.b2body.setTransform(currentPosition.add(JUMP_SIZE, 0), 0);
            imgFrog = new Texture("frog/frog_right.png");
        }
    }

    public void die(boolean frogDied) {
        this.frogDied = frogDied;
        lives = lives - 1;
        imgFrog = new Texture("frog/frog_up.png");
    }

    public void hudLives(){
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        livesCountLabel.setText(lives.toString());

        //aumenta tamanho da fonte
        livesLabel.setFontScale(2);
        livesCountLabel.setFontScale(2);

        table.add().expandX();
        table.add().expandX();
        table.add(livesLabel).expandX();
        table.row();
        table.add().expandX().padBottom(10);
        table.add().expandX().padBottom(10);
        table.add(livesCountLabel).expandX().padBottom(10);

        stage.addActor(table);
        stage.draw();
    }

    @Override
    public void dispose() {
        Sounds.dispose();
        stage.dispose();
    }

}
