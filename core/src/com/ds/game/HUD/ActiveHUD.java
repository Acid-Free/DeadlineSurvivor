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
import com.ds.game.Furnitures.*;

public class ActiveHUD {
    private Player player;
    public Stage stage;
    public Viewport viewport;

    private Label computerLabel;
    private Label kitchenLabel;
    private Label studyTableLabel;
    private Label televisionLabel;
    private Label bedLabel;


    public ActiveHUD(SpriteBatch sb, Player player){
        this.player = player;

        viewport = new FitViewport(DeadlineSurvivor.SCREEN_WIDTH, DeadlineSurvivor.SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.right();
        table.top();
        table.setFillParent(true);

        computerLabel = new Label(String.format("Computer: %-8s", Computer.isActive ? "active" : "inactive"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        kitchenLabel = new Label(String.format("Kitchen: %-8s", Kitchen.isActive ? "active" : "inactive"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        studyTableLabel = new Label(String.format("Study Table: %s-8", StudyTable.isActive ? "active" : "inactive"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        televisionLabel = new Label(String.format("Television: %-8s", Television.isActive ? "active" : "inactive"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bedLabel = new Label(String.format("Bed: %-8s", Bed.isActive ? "active" : "inactive"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(computerLabel).right().padTop(320);
        table.row();
        table.add(kitchenLabel).right().padTop(10);
        table.row();
        table.add(studyTableLabel).right().padTop(10);
        table.row();
        table.add(televisionLabel).right().padTop(10);
        table.row();
        table.add(bedLabel).right().padTop(10);

        stage.addActor(table);
    }

    public void update(){
        computerLabel.setText(String.format("Computer: %-8s", Computer.isActive ? "active" : "inactive"));
        kitchenLabel.setText(String.format("Kitchen: %-8s", Kitchen.isActive ? "active" : "inactive"));
        studyTableLabel.setText(String.format("Study Table: %-8s", StudyTable.isActive ? "active" : "inactive"));
        televisionLabel.setText(String.format("Television: %-8s", Television.isActive ? "active" : "inactive"));
        bedLabel.setText(String.format("Bed: %-8s", Bed.isActive ? "active" : "inactive"));
    }
}
