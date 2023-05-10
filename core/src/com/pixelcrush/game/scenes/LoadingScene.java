package com.pixelcrush.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.GameScene;

public class LoadingScene extends ScreenAdapter {

    public LoadingScene(){
        PixelCrushCore.manager.load("LoadingScreen/loading-screen.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (PixelCrushCore.manager.update()){
            PixelCrushCore.INSTANCE.setScreen(new GameScene());
        }
    }
}
