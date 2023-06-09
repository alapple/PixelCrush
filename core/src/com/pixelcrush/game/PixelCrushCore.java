package com.pixelcrush.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.pixelcrush.game.scenes.LoadingScene;

public class PixelCrushCore extends Game {
    public static AssetManager manager;
    public static PixelCrushCore INSTANCE;

    public PixelCrushCore() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        setScreen(new LoadingScene());
    }
}
