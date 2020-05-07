package com.ds.game.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ds.game.DeadlineSurvivor;
import com.ds.game.Entities.Player;

public class MainHUD {
    private Player player;
    public Stage stage;
    public Viewport viewport;

    private Label timeBeforeDeadline;
    private Label overallTimeLabel;
    private Label currentHealthLabel;
    private Label currentElectricityLabel;
    private Label currentFocusLabel;
    private Label currentFunLabel;
    private Label currentSatietyLabel;
    private Label currentDayLabel;
    private Label currentFPS;

    public MainHUD(SpriteBatch sb, Player player){
        this.player = player;

        viewport = new FitViewport(DeadlineSurvivor.SCREEN_WIDTH, DeadlineSurvivor.SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.left();
        table.top();
        table.setFillParent(true);

        timeBeforeDeadline = new Label(String.format("Deadline in Day %d", (int)player.deadlineTime / 86400 + 1)
                , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentDayLabel = new Label(String.format("Day %d %s", (int)(player.overallTime / 86400 + 1),
                (player.deadlineTime <= player.overallTime ? "(Deadline)" : "")), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overallTimeLabel = new Label(String.format("Time: %02d:%02d", (int)(player.overallTime % 86400 / 3600),
                MathUtils.round(player.overallTime % 3600 / 60)),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentHealthLabel = new Label(String.format("Health: %3d", (int)(player.currentHealth)),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentElectricityLabel = new Label(String.format("Electricity: %4d", (int)(player.currentElectricity)),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentFocusLabel = new Label(String.format("Focus: %3d", (int)(player.currentFocus)),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentFunLabel = new Label(String.format("Fun: %3d", (int)(player.currentFun)),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentSatietyLabel = new Label(String.format("Satiety: %3d", (int)(player.currentSatiety)),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentFPS = new Label(String.format("FPS: %02d", Gdx.graphics.getFramesPerSecond()),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(timeBeforeDeadline).left().padTop(10);
        table.row();
        table.add(currentDayLabel).left().padTop(3);
        table.row();
        table.add(overallTimeLabel).left().padTop(3);
        table.row();
        table.add(currentHealthLabel).left().padTop(30);
        table.row();
        table.add(currentElectricityLabel).left().padTop(5);
        table.row();
        table.add(currentFocusLabel).left().padTop(5);
        table.row();
        table.add(currentFunLabel).left().padTop(5);
        table.row();
        table.add(currentSatietyLabel).left().padTop(5);

        table.row();
        table.add(currentFPS).left().padTop(240);

        stage.addActor(table);
    }

    public void update(){
        if(player.deadlineTime <= player.overallTime)
            timeBeforeDeadline.setText("Deadline is TODAY");
        else
            timeBeforeDeadline.setText(String.format("Deadline in Day %d", (int)player.deadlineTime / 86400 + 1));
        currentDayLabel.setText(String.format("Day %d %s", (int)(player.overallTime / 86400 + 1),
                (player.deadlineTime <= player.overallTime ? "(Deadline)" : "")));
        overallTimeLabel.setText(String.format("Time: %02d:%02d", (int)(player.overallTime % 86400 / 3600),
                MathUtils.round(player.overallTime % 3600 / 60)));
        currentHealthLabel.setText(String.format("Health: %3d", (int)(player.currentHealth)));
        currentElectricityLabel.setText(String.format("Electricity: %4d", (int)(player.currentElectricity)));
        currentFocusLabel.setText(String.format("Focus: %3d", (int)(player.currentFocus)));
        currentFunLabel.setText(String.format("Fun: %3d", (int)(player.currentFun)));
        currentSatietyLabel.setText(String.format("Satiety: %3d", (int)(player.currentSatiety)));
        currentFPS.setText(String.format("FPS: %02d", Gdx.graphics.getFramesPerSecond()));
    }
}
