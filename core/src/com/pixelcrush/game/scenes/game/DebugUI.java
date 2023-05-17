package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.pixelcrush.game.PixelCrushCore;

public class DebugUI {
    private final Skin uiSkin = PixelCrushCore.manager.get("data/uiskin.json");
    private final Label fpsLabel = new Label("Collecting...", uiSkin);

    public DebugUI(Stage stage) {
        stage.addActor(fpsLabel);
        handleResize(0, Gdx.graphics.getHeight());
    }

    public void updateFPSText() {
        fpsLabel.setText("%d fps".formatted(Gdx.graphics.getFramesPerSecond()));
    }

    public void handleResize(int width, int height) {
        fpsLabel.setPosition(0, height - 25);
    }
}
