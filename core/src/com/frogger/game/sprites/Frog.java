package com.frogger.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.FroggerGame;
import com.frogger.game.Player;
import com.frogger.game.StageGame;
import com.frogger.game.screens.MainGameScreen;

public class Frog extends Sprite implements Disposable {
    private World world;
    private Body b2body;
    private MainGameScreen screen;
    private Texture imgFrog;
    private TextureRegion frogStand;
    private TextureRegion frogRight;
    private TextureRegion frogLeft;
    private TextureRegion frogDown;


    public boolean frogDied;
    private Viewport viewport;
    private Stage stage;

    private final Vector2 positionInitial;
    private final Integer JUMP_SIZE = 48;
    private final Integer SIZE_OF_FROG = 44;
    private final Integer RADIUS_OF_FROG = 22; //ANTIGO SIZE_OF_FROG
    private final Integer SIZE_BOX_FROG = 48;

    public Integer lives;
    private Label livesCountLabel;
    private Label livesLabel;

    // adicionar argumento no construtor do frog: ele tem q receber a screen de gameover
    // ver comentario na linha 106
    public Frog(World world, int lives, SpriteBatch sb, MainGameScreen screen){
        super(screen.getAtlas().findRegion("frog_up"));
        this.screen = screen;
        this.world = world;
        this.lives = lives;
        viewport = new FitViewport(FroggerGame.screenWidth, FroggerGame.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        positionInitial = new Vector2(Gdx.graphics.getWidth() / 2,120);
        livesCountLabel = new Label(String.format("%01d",lives),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        defineFrog();
        frogStand = new TextureRegion(getTexture(), 13*(SIZE_BOX_FROG), 0, SIZE_BOX_FROG, SIZE_BOX_FROG);
        frogRight = new TextureRegion(getTexture(), 12*(SIZE_BOX_FROG), 0, SIZE_BOX_FROG, SIZE_BOX_FROG);
        frogLeft = new TextureRegion(getTexture(), 11*(SIZE_BOX_FROG), 0, SIZE_BOX_FROG, SIZE_BOX_FROG);
        frogDown = new TextureRegion(getTexture(), 10*(SIZE_BOX_FROG), 0, SIZE_BOX_FROG, SIZE_BOX_FROG);
        setBounds(0 ,0, SIZE_OF_FROG, SIZE_OF_FROG);
        setRegion(frogStand);
    }

    private void defineFrog(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(positionInitial);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RADIUS_OF_FROG,RADIUS_OF_FROG);
        fdef.shape = shape;
        //fdef.isSensor = true;
        b2body.createFixture(fdef);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - RADIUS_OF_FROG, b2body.getPosition().y - RADIUS_OF_FROG);
    }

    public Stage getStage(){
        return stage;
    }

    public void handlePositionOfFrog(Frog frog) {
        Vector2 currentPosition = new Vector2(frog.b2body.getPosition());
        if (Gdx.input.isKeyJustPressed((Input.Keys.UP))) {
            frog.b2body.setTransform(currentPosition.add(0, JUMP_SIZE), 0);
            setRegion(frogStand);
            if (currentPosition.y >= Gdx.graphics.getHeight()) {
                frog.b2body.setTransform(positionInitial, 0);
                screen.nextStage();
            }
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.DOWN))) {
            if (currentPosition.y > (SIZE_BOX_FROG / 2) + 2*JUMP_SIZE) {
                frog.b2body.setTransform(currentPosition.add(0, -JUMP_SIZE), 0);
                setRegion(frogDown);
            }
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.LEFT))) {
            if (currentPosition.x > (SIZE_BOX_FROG / 2)) {
                frog.b2body.setTransform(currentPosition.add(-JUMP_SIZE, 0), 0);
                setRegion(frogLeft);
            }
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))) {
            if (currentPosition.x < Gdx.graphics.getWidth() - (SIZE_BOX_FROG / 2) - 4) {
                frog.b2body.setTransform(currentPosition.add(JUMP_SIZE, 0), 0);
                setRegion(frogRight);
            }
        } else if(frogDied) {
            frog.b2body.setTransform(positionInitial, 0);
            frogDied = false;
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.W))){
            screen.nextStage();
        }
    }

    public void die(boolean frogDied) {
        this.frogDied = frogDied;
        lives = lives - 1;
    }

    public void hudFrog(){
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
    }



    @Override
    public void dispose() {
        stage.dispose();
    }

}
