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
    SpriteBatch sb;
    Viewport viewport;
    public Stage stage;
    Label scoreLabel;
    Label scoreCountLabel;

    private int score;

    public Player(SpriteBatch sb){
        score = 0;

        viewport = new FitViewport(FroggerGame.V_WIDTH, FroggerGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreCountLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

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
