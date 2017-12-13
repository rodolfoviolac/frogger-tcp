package com.frogger.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player {
    private Viewport viewport;
    private Stage stage;
    private Integer score;
    private Label scoreLabel;
    private Label scoreCountLabel;

    public Player(SpriteBatch sb){
        score = 0;
        viewport = new FitViewport(FroggerGame.screenWidth, FroggerGame.screenHeight,new OrthographicCamera());
        stage = new Stage(viewport, sb);
        scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreCountLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    public void hudScore(){
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        scoreCountLabel.setText(score.toString());

        scoreLabel.setFontScale(2);
        scoreCountLabel.setFontScale(2);

        table.add(scoreLabel).expandX();
        table.add().expandX();
        table.add().expandX();
        table.row();
        table.add(scoreCountLabel).expandX().padBottom(10);
        table.add().expandX().padBottom(10);
        table.add().expandX().padBottom(10);

        stage.addActor(table);
        stage.draw();
    }

    public int getScore(){
        return score;
    }

    public void setScore(int stageTimer, int level){
        score = score + (stageTimer * level);
    }
}
