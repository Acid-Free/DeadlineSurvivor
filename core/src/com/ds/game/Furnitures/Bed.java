package com.ds.game.Furnitures;

import com.badlogic.gdx.math.MathUtils;
import com.ds.game.Entities.Player;

public class Bed {

    public static boolean isActive;

    public static String description;
    public static float timeBeforeSleeping;

    public Bed(){
        description = "A device that lets you rest.";
    }

    public void sleep(Player player){
        float totalTimeSlept = player.overallTime += 28800 + MathUtils.random(-3000, 3000);
        player.currentFocus = 100;
        player.currentFun = 90 + MathUtils.random(-20, 20);
        player.currentSatiety = totalTimeSlept * player.satietyVariable;
    }
}
