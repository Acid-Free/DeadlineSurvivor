package com.ds.game.Furnitures;

import com.badlogic.gdx.math.MathUtils;

public class Kitchen {

    public static boolean isActive;

    public static int foodNumber;
    public float fun;
    public float electricityConsumption;
    public static String description;

    public static float stayingTimeBeforeEating;

    public Kitchen(int foodNumber){
        this.foodNumber = foodNumber;
        electricityConsumption = -0.4f;
        fun = 1/5f;
        description = "A treasure box full of energy.";
    }

    public int eat(){
        int nourishment = 0;
        if(foodNumber <= 0){
            description = "An empty treasure box...";
        }
        else{
            foodNumber--;
            nourishment += MathUtils.random(30, 50);
        }

        return nourishment;
    }
}
