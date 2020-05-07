package com.ds.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ds.game.FinalState;

public class Player extends Sprite {

    public int currentMoney;

    //1 second IRL = 480 seconds in game
    public int timeScale = 480;

    //in seconds
    public float deadlineTime;
    public float overallTime;
    public float timeCount;

    public String name;
    public FinalState finalState;
    public String finalMessage;

    public float currentHealth;
    public float currentElectricity;
    public float currentFocus;
    public float currentFun;
    public float currentSatiety;

    public String currentInteraction;

    //timeScale already applied
    public float focusVariable = -2/15f;
    public float funVariable = -4/3f;
    public float satietyVariable = -2/3f;

    public float healthFactorToFocus;
    public float satietyFactorToFocus;
    public float funFactorToFocus;
    public float satietyFactorToHealth;
    public float furnitureFactorToFun;


    public World world;
    public Body b2body;

    public int startX;
    public int startY;

    public Player(World world){
        deadlineTime = 518400f ;

        currentHealth = 100;
        overallTime = 28800;
        timeCount = 0;
        currentElectricity = 1500f;
        currentFocus = 100f;
        currentFun = 100f;
        currentSatiety = 100f;

        startX = 320;
        startY = 325;

        this.world = world;
        definePlayer();
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(startX, startY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setUserData("Player");

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(18);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void update(float delta){

        if(currentHealth > 90)
            healthFactorToFocus = 1/4f;
        else if(currentHealth > 70)
            healthFactorToFocus = 1/10f;
        else if(currentHealth > 40)
            healthFactorToFocus = 0;
        else if(currentHealth > 15)
            healthFactorToFocus = -2/3f;
        else
            healthFactorToFocus = -5/2f;

        if(currentSatiety > 90) {
            satietyFactorToFocus = 2/5f;
            satietyFactorToHealth = 2/5f;
        }
        else if(currentSatiety > 60) {
            satietyFactorToFocus = 1/9f;
            satietyFactorToHealth = 1/4f;
        }
        else if(currentSatiety > 15) {
            satietyFactorToFocus = 0;
            satietyFactorToHealth = 0;
        }
        else if(currentSatiety > 1) {
            satietyFactorToFocus = -2/5f;
            satietyFactorToHealth = -2/3f;
        }
        else {
            satietyFactorToFocus = -2/3f;
            satietyFactorToHealth = -2f;
        }

        if(currentFun > 90)
            funFactorToFocus = 2/3f;
        else if(currentFun > 70)
            funFactorToFocus = 1/6f;
        else if(currentFun > 40)
            funFactorToFocus = 0;
        else if(currentFun > 10)
            funFactorToFocus = -2/5f;
        else if(currentFun > 1)
            funFactorToFocus = -4/3f;
        else
            funFactorToFocus = -2f;

        currentHealth += delta * satietyFactorToHealth;
        currentFocus += delta * (focusVariable + satietyFactorToFocus + funFactorToFocus + healthFactorToFocus);
        currentFun += delta * (funVariable + furnitureFactorToFun);
        currentSatiety += delta * satietyVariable;

        if(currentHealth < 0)
            currentHealth = 0;
        if(currentFocus < 0)
            currentFocus = 0;
        if(currentFun < 0)
            currentFun = 0;
        if(currentSatiety < 0)
            currentSatiety = 0;
    }
}
