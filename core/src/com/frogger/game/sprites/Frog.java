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
    private World world;
    private Body b2body;
    private MainGameScreen screen;

    private boolean frogDied;
    private Viewport viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT,new OrthographicCamera());
    private Stage stage;

    private final Vector2 positionInitial = new Vector2(Gdx.graphics.getWidth() / 2,120);
    private final Integer JUMP_SIZE = 48;
    private final Integer SIZE_OF_FROG = 22;

    private Integer lives;
    private Label livesCountLabel;
    private Label livesLabel;

    public Frog(World world, int lives, SpriteBatch sb, MainGameScreen screen){
        this.screen = screen;
        this.world = world;
        this.lives = lives;
        stage = new Stage(viewport, sb);
        livesCountLabel = new Label(String.format("%01d",lives),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        defineFrog();
    }

    private void defineFrog(){
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

    public Stage getStage(){
        return stage;
    }

    public void handlePositionOfFrog(Frog frog) {
        Vector2 currentPosition = new Vector2(frog.b2body.getPosition());
        if (Gdx.input.isKeyJustPressed((Input.Keys.UP))) {
            if (currentPosition.y == Gdx.graphics.getHeight() - (JUMP_SIZE / 2)) {
                frog.b2body.setTransform(positionInitial, 0);
                screen.nextStage();
            } else if (currentPosition.y < Gdx.graphics.getHeight() - SIZE_OF_FROG - 2) {
                frog.b2body.setTransform(currentPosition.add(0, JUMP_SIZE), 0);
            }
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.DOWN))) {
            if (currentPosition.y > SIZE_OF_FROG + 2) {
                frog.b2body.setTransform(currentPosition.add(0, -JUMP_SIZE), 0);
            }
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.LEFT))) {
            if (currentPosition.x > SIZE_OF_FROG + 2) {
                frog.b2body.setTransform(currentPosition.add(-JUMP_SIZE, 0), 0);
            }
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))) {
            if (currentPosition.x < Gdx.graphics.getWidth() - SIZE_OF_FROG - 2) {
                frog.b2body.setTransform(currentPosition.add(JUMP_SIZE, 0), 0);
            }
        } else if(frogDied) {
            frog.b2body.setTransform(positionInitial, 0);
            frogDied = false;
        } else if (Gdx.input.isKeyJustPressed((Input.Keys.W))) screen.nextStage();
    }

    public void die(boolean frogDied) {
        this.frogDied = frogDied;
        lives = lives - 1;
        Gdx.app.log("Morreu", "...");
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
