package com.ds.game.Requirements;

import com.badlogic.gdx.math.MathUtils;
import com.ds.game.Entities.Player;
import com.ds.game.Requirement;
import com.ds.game.Subject;

public class Project implements Requirement {
    private double maxHealthPoints;
    private double healthPoints;
    private Subject subject;
    private int difficulty;
    private int fun;
    public boolean requirementDone;

    public Project(){
        int subjectBasis = MathUtils.random(2);

        maxHealthPoints = 8000 + MathUtils.random(-3, 2) * 100;
        healthPoints = maxHealthPoints;

        switch(subjectBasis){
            case 0:
                subject = Subject.MATHEMATICS;
                break;
            case 1:
                subject = Subject.PROGRAMMING;
                break;
            case 2:
                subject = Subject.SCIENCE;
                break;
        }

        difficulty = MathUtils.random(subjectData[subjectBasis][0], subjectData[subjectBasis][1]);
        fun = MathUtils.random(subjectData[subjectBasis][2],subjectData[subjectBasis][3]);
    }

    @Override
    public void doRequirement(Player player, float delta) {

        if(player.currentFocus > 80){
            progress(player.currentFocus, player.currentFun, 10 + MathUtils.random(-2, 2), delta);
        }
        else if(player.currentFocus > 50){
            progress(player.currentFocus, player.currentFun, 6 + MathUtils.random(-3, 2), delta);
        }
        else if(player.currentFocus > 20){
            progress(player.currentFocus, player.currentFun, 4 + MathUtils.random(-3, 1), delta);
        }
        else if(player.currentFocus > 5){
            progress(player.currentFocus, player.currentFun, 3 + MathUtils.random(-3, 0), delta);
        }
        else{
            progress(player.currentFocus, player.currentFun, 2 + MathUtils.random(-2, -1), delta);
        }
    }

    @Override
    public void progress(double focus, double fun, int impact, float delta) {
        healthPoints -= (focus * impact / 50 - (100 - fun) / 40) * delta;
        if(healthPoints < 0)
            requirementDone = true;
    }

    @Override
    public double getProgress() {
        return (maxHealthPoints - healthPoints) / maxHealthPoints * 100;
    }

    @Override
    public double getHealthPoints() {
        return healthPoints;
    }

    @Override
    public Subject getSubject() {
        return subject;
    }
}
