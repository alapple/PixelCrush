package com.pixelcrush.game.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.GameScene;

public class LoadingScene extends ScreenAdapter {

    public LoadingScene(){
        PixelCrushCore.manager.load("LoadingScreen/loading-screen.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (PixelCrushCore.manager.update()){
            PixelCrushCore.INSTANCE.setScreen(new GameScene());
        }
    }
}
