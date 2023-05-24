package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pixelcrush.game.DebugConfig;
import com.pixelcrush.game.PixelCrushCore;

public class DebugUI {
    private final Skin uiSkin = PixelCrushCore.manager.get("data/uiskin.json");
    private final Label fpsLabel = new Label("Collecting...", uiSkin);
    private final TextButton showDebugButton = new TextButton("Show Debug UI", uiSkin, "toggle");

    public DebugUI(Stage stage) {
        showDebugButton.setChecked(DebugConfig.DEBUG_RENDER);
        showDebugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DebugConfig.DEBUG_RENDER = showDebugButton.isChecked();
                handleResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
        });
        stage.addActor(showDebugButton);
        stage.addActor(fpsLabel);
        handleResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update() {

    }

    public void updateFPSText() {
        fpsLabel.setText("%d fps".formatted(Gdx.graphics.getFramesPerSecond()));
    }

    public void handleResize(int width, int height) {
        fpsLabel.setPosition(0, height - fpsLabel.getHeight() - 5);
        showDebugButton.setPosition(width - showDebugButton.getWidth() - 5, height - showDebugButton.getHeight() - 5);
    }
}
