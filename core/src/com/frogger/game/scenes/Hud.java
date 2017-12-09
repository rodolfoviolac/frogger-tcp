package com.frogger.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.frogger.game.FroggerGame;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private float timeCount;
    private Integer score;
    private Integer lives;
    private Integer worldTimer;

    Label scoreCountLabel;
    Label scoreLabel;
    Label timeCountLabel;
    Label timeLabel;
    Label livesCountLabel;
    Label livesLabel;

    public Hud(SpriteBatch sb){
        timeCount = 0;
        score = 0;
        lives = 0;
        worldTimer = 300;

        viewport = new FitViewport(FroggerGame.screenWidth, FroggerGame.screenHeight,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreCountLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeCountLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesCountLabel = new Label(String.format("%01d",lives),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel.setFontScale(2);
        scoreCountLabel.setFontScale(2);
        timeLabel.setFontScale(2);
        timeCountLabel.setFontScale(2);
        livesLabel.setFontScale(2);
        livesCountLabel.setFontScale(2);

        table.add(scoreLabel).expandX();
        table.add(timeLabel).expandX();
        table.add(livesLabel).expandX();
        table.row();
        table.add(scoreCountLabel).expandX().padBottom(10);
        table.add(timeCountLabel).expandX().padBottom(10);
        table.add(livesCountLabel).expandX().padBottom(10);

        stage.addActor(table);

    }

}
