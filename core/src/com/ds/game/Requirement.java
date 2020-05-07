package com.ds.game;

import com.ds.game.Entities.Player;

public interface Requirement {

    //subject and data respectively
    int[][]subjectData = {
            {5, 10, -5, 0},
            {2, 10, -2, 3},
            {3, 8, -3, -1}
    };

    void doRequirement(Player player, float delta);
    //deprecate this later
    void progress(double focus, double fun, int impact, float delta);
    double getProgress();
    double getHealthPoints();
    Subject getSubject();
}
