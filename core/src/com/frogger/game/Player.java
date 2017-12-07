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
    private Viewport viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT,new OrthographicCamera());
    private Stage stage;

    private Integer score = 0;
    private Label scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Label scoreCountLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    public Player(SpriteBatch sb){
        stage = new Stage(viewport, sb);
    }

    public Stage getStage(){
        return stage;
    }

    public void hudPlayer(){
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
    }

    public int getScore(){
        return score;
    }

    public void setScore(int scoreOnStage){
        score = score + scoreOnStage;
    }
}
