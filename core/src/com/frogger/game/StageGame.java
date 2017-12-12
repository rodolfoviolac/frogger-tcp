package com.frogger.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StageGame {
    private Lane lanes[];
    private final int NUM_OF_LANES = 14;
    private final int IS_LANE[] = {3, 4, 5, 6, 7, 9, 10, 11, 12, 13};
    private final int DISTANCE_OF_LANES = 48;
    private float timeCount;
    private Integer stageTimer;
    private int level;
    private Label timeLabel;
    private Label timeCountLabel;
    private Viewport viewport;
    private Stage stage;

    public StageGame(World world, SpriteBatch sb, int level) {
        viewport = new FitViewport(FroggerGame.screenWidth, FroggerGame.screenHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        lanes = new Lane[NUM_OF_LANES];
        this.level = level;
        timeCount = 0;
        stageTimer = 50;
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeCountLabel = new Label(String.format("%02d", stageTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        sortDirectionLanes(world, level);
    }

    public void draw(SpriteBatch sb){
        for (int i : IS_LANE){
            lanes[i].draw(sb);
        }
    }

    public int changeLane(int numOfLane){
        int changeToLane = 0;
        for (int i : IS_LANE){
            if (i == numOfLane + 1){
                if (lanes[numOfLane].getDirection().equals(lanes[i].getDirection())){
                    if (lanes[i].haveSpaceToChange()){
                        changeToLane = 1;
                    }
                }
            } else if (i == numOfLane - 1){
                if (lanes[numOfLane].getDirection().equals(lanes[i].getDirection())){
                    if (lanes[i].haveSpaceToChange())
                        changeToLane = -1;
                }
            }
        }
        return changeToLane * DISTANCE_OF_LANES;
    }

    public void update(float dt) {
        for (int i : IS_LANE) {
            lanes[i].update();
        }
        if(stageTimer <= 0){
            timeCount = 0;
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

    public void hudTime() {
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
        stage.draw();
    }

    private void sortDirectionLanes(World world, int level) {
        String direction;

        for (int numOfLane : IS_LANE) {
            int iDirection = 1 + (int) (Math.random() * 2);
            if (iDirection == 1) {
                direction = "left";
            } else direction = "right";
            lanes[numOfLane] = new Lane(world, DISTANCE_OF_LANES, level, direction, this, numOfLane);
        }
    }

    public void dispose(World world) {
        for (int i : IS_LANE) {
            lanes[i].dispose(world);
        }
    }
}
