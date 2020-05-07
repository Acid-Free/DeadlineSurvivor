package com.ds.game.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ds.game.DeadlineSurvivor;
import com.ds.game.Entities.Player;
import com.ds.game.Furnitures.*;
import com.ds.game.Screens.GameScreen;

public class FurnitureHUD {
    private Player player;
    public Stage stage;
    public Viewport viewport;
    private SpriteBatch sb;

    private Table table;

    private Label interactionLabel;
    private Label descriptionLabel;
    private String activeFurnitureName = "";

    private String lastFurniture = "";

    public FurnitureHUD(SpriteBatch sb, Player player){
        this.player = player;

        viewport = new FitViewport(DeadlineSurvivor.SCREEN_WIDTH, DeadlineSurvivor.SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table = new Table();
        table.right();
        table.top();
        table.setFillParent(true);

        interactionLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        descriptionLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(interactionLabel).right().padTop(10);
        table.row();
        table.add(descriptionLabel).right().padTop(10);


        stage.addActor(table);

        if(player.currentInteraction != null)
            switch(player.currentInteraction){
                case "kitchenArea":
                    activeFurnitureName = "Kitchen";
                    kitchenHUD();
                    break;
                case "bedArea":
                    activeFurnitureName = "Bed";
                    break;
                case "restRoomArea":
                    activeFurnitureName = "Restroom";
                    break;
                case "studyArea":
                    activeFurnitureName = "Study Desk";
                    break;
                case "computerArea":
                    activeFurnitureName = "Computer";
                    break;
                case "televisionArea":
                    activeFurnitureName = "Television";
                    break;
                case "doorArea":
                    activeFurnitureName = "Door";
                    break;
                case "wall":
                    activeFurnitureName = "Wall";
                    break;
                default:
                    activeFurnitureName = "";
            }


    }

    public void update(){
        if(player.currentInteraction != null)
            switch(player.currentInteraction){
                case "kitchenArea":
                    activeFurnitureName = "Kitchen";
                    kitchenUpdate();
                    break;
                case "bedArea":
                    activeFurnitureName = "Bed";
                    bedUpdate();
                    break;
                case "restRoomArea":
                    activeFurnitureName = "Restroom";
                    break;
                case "studyArea":
                    activeFurnitureName = "Study Desk";
                    studyTableUpdate();
                    break;
                case "computerArea":
                    computerUpdate();
                    activeFurnitureName = "Computer";
                    break;
                case "televisionArea":
                    activeFurnitureName = "Television";
                    televisionUpdate();
                    break;
                case "doorArea":
                    activeFurnitureName = "Door";
                    break;
                case "wall":
                    activeFurnitureName = "Wall";
                    if(!lastFurniture.equals(activeFurnitureName)) {
                        disposeCurrentStage();
                        lastFurniture = activeFurnitureName;
                    }
                    interactionLabel.setText(activeFurnitureName);
                    break;
                default:
                    activeFurnitureName = "";
                    deactivateAllFurnitures();
                    if(!lastFurniture.equals(activeFurnitureName)) {
                        disposeCurrentStage();
                        lastFurniture = activeFurnitureName;
                    }
                    interactionLabel.setText(activeFurnitureName);
            }

    }

    private Label kitchenFoodNumber;
    private Label kitchenTimeBeforeEatingLabel;

    public void kitchenHUD(){
        stage = new Stage(viewport, sb);

        table = new Table();
        table.right();
        table.top();
        table.setFillParent(true);

        interactionLabel = new Label(activeFurnitureName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(interactionLabel).right().padTop(10);

        stage.addActor(table);
    }


    public void kitchenUpdate() {
        if(!lastFurniture.equals(activeFurnitureName)) {
            deactivateAllFurnitures();
            Kitchen.isActive = true;
            disposeCurrentStage();

            table = new Table();
            table.right();
            table.top();
            table.setFillParent(true);

            interactionLabel = new Label(activeFurnitureName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            descriptionLabel = new Label(Kitchen.description, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            kitchenFoodNumber = new Label(String.format("Food left : %d", Kitchen.foodNumber),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            kitchenTimeBeforeEatingLabel = new Label(String.format("Eating food in %d...", 2 - (int)Kitchen.stayingTimeBeforeEating),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(interactionLabel).right().padTop(10);
            table.row();
            table.add(descriptionLabel).right().padTop(10);
            table.row();
            table.add(kitchenFoodNumber).right().padTop(20);
            table.row();
            table.add(kitchenTimeBeforeEatingLabel).right().padTop(20);

            stage.addActor(table);

            lastFurniture = activeFurnitureName;
        }
        else{
            Kitchen.isActive = true;
            interactionLabel.setText(activeFurnitureName);
            descriptionLabel.setText(Kitchen.description);
            kitchenFoodNumber.setText(String.format("Food left : %d", Kitchen.foodNumber) );
            kitchenTimeBeforeEatingLabel.setText(String.format("Eating food in %d...", 2 - (int)Kitchen.stayingTimeBeforeEating));
        }
    }


    private Label timeBeforeSleepingLabel;

    public void bedUpdate() {
        if(!lastFurniture.equals(activeFurnitureName)) {
            deactivateAllFurnitures();
            Bed.isActive = true;
            disposeCurrentStage();

            table = new Table();
            table.right();
            table.top();
            table.setFillParent(true);

            interactionLabel = new Label(activeFurnitureName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            descriptionLabel = new Label(Bed.description, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            timeBeforeSleepingLabel = new Label(String.format("Sleeping in %d...", 3 - (int)Bed.timeBeforeSleeping),
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(interactionLabel).right().padTop(10);
            table.row();
            table.add(descriptionLabel).right().padTop(10);
            table.row();
            table.add(timeBeforeSleepingLabel).right().padTop(20);

            stage.addActor(table);

            lastFurniture = activeFurnitureName;
        }
        else{
            Bed.isActive = true;
            interactionLabel.setText(activeFurnitureName);
            descriptionLabel.setText(Bed.description);
            timeBeforeSleepingLabel.setText(String.format("Sleeping in %d...", 3 - (int)Bed.timeBeforeSleeping));
        }
    }

    public void computerUpdate() {
        if(!lastFurniture.equals(activeFurnitureName)) {
            deactivateAllFurnitures();
            Computer.isActive = true;
            disposeCurrentStage();

            table = new Table();
            table.right();
            table.top();
            table.setFillParent(true);

            interactionLabel = new Label(activeFurnitureName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            descriptionLabel = new Label(Computer.description, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(interactionLabel).right().padTop(10);
            table.row();
            table.add(descriptionLabel).right().padTop(10);
            table.row();

            stage.addActor(table);

            lastFurniture = activeFurnitureName;
        }
        else{
            Computer.isActive = true;
            interactionLabel.setText(activeFurnitureName);
            descriptionLabel.setText(Computer.description);
        }
    }

    private Label subjectLabel;
    private Label progressLabel;
    private Label healthPointsLeft;

    public void studyTableUpdate() {
        if(!lastFurniture.equals(activeFurnitureName)) {
            deactivateAllFurnitures();
            StudyTable.isActive = true;
            disposeCurrentStage();

            table = new Table();
            table.right();
            table.top();
            table.setFillParent(true);

            interactionLabel = new Label(activeFurnitureName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            descriptionLabel = new Label(StudyTable.description, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            subjectLabel = new Label(String.format("%s Project", GameScreen.mainProject.getSubject()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            progressLabel = new Label(String.format("Progress: %.02f%%", GameScreen.mainProject.getProgress()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            healthPointsLeft = new Label(String.format("HP Left: %d", (int)GameScreen.mainProject.getHealthPoints()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(interactionLabel).right().padTop(10);
            table.row();
            table.add(descriptionLabel).right().padTop(10);
            table.row();
            table.add(subjectLabel).right().padTop(20);
            table.row();
            table.add(progressLabel).right().padTop(5);
            table.row();
            table.add(healthPointsLeft).right().padTop(5);

            stage.addActor(table);

            lastFurniture = activeFurnitureName;
        }
        else{
            StudyTable.isActive = true;
            interactionLabel.setText(activeFurnitureName);
            descriptionLabel.setText(StudyTable.description);
            subjectLabel.setText(String.format("%s Project", GameScreen.mainProject.getSubject()));
            progressLabel.setText(String.format("Progress: %.02f%%", GameScreen.mainProject.getProgress()));
            healthPointsLeft.setText(String.format("HP Left: %d", (int)GameScreen.mainProject.getHealthPoints()));
        }
    }

    public void televisionUpdate() {
        if(!lastFurniture.equals(activeFurnitureName)) {
            deactivateAllFurnitures();
            Television.isActive = true;
            disposeCurrentStage();

            table = new Table();
            table.right();
            table.top();
            table.setFillParent(true);

            interactionLabel = new Label(activeFurnitureName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            descriptionLabel = new Label(Television.description, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(interactionLabel).right().padTop(10);
            table.row();
            table.add(descriptionLabel).right().padTop(10);

            stage.addActor(table);

            lastFurniture = activeFurnitureName;
        }
        else{
            Television.isActive = true;
            interactionLabel.setText(activeFurnitureName);
            descriptionLabel.setText(Television.description);
        }
    }

    public void disposeCurrentStage(){
//        System.out.println("Disposing current stage.");
        stage.dispose();
    }

    public void deactivateAllFurnitures(){
        Bed.timeBeforeSleeping = 0;
        Kitchen.stayingTimeBeforeEating = 0;

        Bed.isActive = false;
        Computer.isActive = false;
        Kitchen.isActive = false;
        StudyTable.isActive = false;
        Television.isActive = false;
    }
}
