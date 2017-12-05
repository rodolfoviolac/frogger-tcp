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

public class StageGame implements Disposable{
    World world;
    Lane lanes[] = new Lane[15];
    int timeCount;
    Label timeLabel;
    Label timeCountLabel;
    Viewport viewport;
    public Stage stage;

    public StageGame(World world, int level) {
        Gdx.app.log("aqui", String.valueOf(level));
        String direction;
        for (int i = 0; i <= 13; i++) {
            if (i == 3 || i == 6 || i == 7 || i == 9 || i == 11){
                direction = "right";
            } else {
                direction = "left";
            }
            if (i > 2 && i != 8)
                lanes[i] = new Lane(world, i * 48 + 24, level, direction);
        }
    }




    public void update(){
        for(int i=0;i<=13;i++) {
            if (i > 2 && i != 8) {
                lanes[i].update();
            }
        }
    }

    public void hudStage(SpriteBatch sb){
        viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeCountLabel = new Label(String.format("%01d",timeCount),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        timeLabel.setFontScale(2);
        timeCountLabel.setFontScale(2);

        table.add().expandX();
        table.add().expandX();
        table.add(timeLabel).expandX();
        table.row();
        table.add().expandX().padBottom(10);
        table.add().expandX().padBottom(10);
        table.add(timeCountLabel).expandX().padBottom(10);

        stage.addActor(table);

    }


    @Override
    public void dispose() {
        for(int i=0;i<=13;i++) {
            if (i > 2 && i != 8){
                lanes[i].dispose();
            }
        }
    }
}
