package com.pixelcrush.game.scenes.game.enemy;

import java.util.ArrayList;
import java.util.List;

public abstract class GameStageManager {
    private int currentStage = 0;
    private ArrayList<Stage> stages = new ArrayList<Stage>(List.of(new Stage(1, 1, 3)));

    private int getCurrentStage() {
        return currentStage;
    }

    public void continueToNextStage() {
        currentStage++;
        nextStage(stages.get(currentStage - 1));
    }

    public abstract void nextStage(Stage stage);
}
