package com.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.screens.MainGameScreen;

import java.util.Random;

public class StageGame implements Disposable {
    Lane lanes[];
    private int NUM_OF_LANES = 14;
    private int IS_LANE[] = {3, 4, 5, 6, 7, 9, 10, 11, 12, 13};
    private int LANE_RADIUS = 24;
    private int DISTANCE_OF_LANES = 48;
    private float timeCount;
    private Integer stageTimer;
    private int level;
    private Label timeLabel;
    private Label timeCountLabel;
    private Viewport viewport;
    private Stage stage;
    private MainGameScreen screen;

    // substituir a MainGameScreen screen que StageGame recebe pela Screen de time Over / Game Over, etc
    // ver comentário na linha 59
    public StageGame(World world, SpriteBatch sb, int level, MainGameScreen screen) {
        this.level = level;
        this.screen = screen;
        viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        lanes = new Lane[NUM_OF_LANES];
        timeCount = 0;
        stageTimer = 10;
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeCountLabel = new Label(String.format("%02d", stageTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        sortDirectionLanes(world, level);
    }

    public Stage getStage(){
        return stage;
    }

    public void update(float dt) {
        for (int i : IS_LANE) {
            lanes[i].update();
        }
        if(stageTimer <= 0){
            timeCount = 0;
            // aqui chamar a screen de TimeOver (setScreen....)
            Gdx.app.log("Time", "over");
        } else {
            timeCount += dt;
            if(timeCount >= 1){
                stageTimer--;
                timeCountLabel.setText(String.format("%02d", stageTimer));
                timeCount = 0;
            }
        }
    }

    public int getStageTimer(){
        return stageTimer;
    }

    public int getLevel(){
        return level;
    }

    public void hudStage() {
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        timeCountLabel.setText(String.format("%02d", stageTimer));

        //aumenta tamanho da fonte
        timeLabel.setFontScale(2);
        timeCountLabel.setFontScale(2);

        table.add().expandX();
        table.add(timeLabel).expandX();
        table.add().expandX();
        table.row();
        table.add().expandX().padBottom(10);
        table.add(timeCountLabel).expandX().padBottom(10);
        table.add().expandX().padBottom(10);

        stage.addActor(table);

    }

    private void sortDirectionLanes(World world, int level) {
        String direction;

        for (int numOfLane : IS_LANE) {
            int iDirection = 1 + (int) (Math.random() * 3);
            if (iDirection == 1) {
                direction = "left";
            } else direction = "right";
            lanes[numOfLane] = new Lane(world, numOfLane * DISTANCE_OF_LANES + LANE_RADIUS, level, direction);
        }
    }

    @Override
    public void dispose() {
        for (int i : IS_LANE) {
            lanes[i].dispose();
        }
    }
}
