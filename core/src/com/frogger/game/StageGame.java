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

import java.util.Random;

public class StageGame implements Disposable {
    Lane lanes[];
    private int NUM_OF_LANES = 14;
    private int IS_LANE[] = {3, 4, 5, 6, 7, 9, 10, 11, 12, 13};
    private int LANE_RADIUS = 24;
    private int DISTANCE_OF_LANES = 48;
    private Integer timeCount;
    private Label timeLabel;
    private Label timeCountLabel;
    private Viewport viewport;
    private Stage stage;

    public StageGame(World world, SpriteBatch sb, int level) {
        viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        lanes = new Lane[NUM_OF_LANES];
        timeCount = 0;
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeCountLabel = new Label(String.format("%01d", timeCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        sortDirectionLanes(world, level);
    }

    public Stage getStage(){
        return stage;
    }

    public void update() {
        for (int i : IS_LANE) {
            lanes[i].update();
        }
    }

    public void hudStage() {
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        timeCountLabel.setText(timeCount.toString());

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
