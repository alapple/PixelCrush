package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScene extends ScreenAdapter {

    Camera cam;
    SpriteBatch batch;
    Player player;
    public GameScene(){
        player = new Player();
        batch = new SpriteBatch();
        cam = new Camera();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.handleInput(delta);
        cam.camFollowPlayer();
        cam.render();
        batch.begin();
        player.render();
        batch.end();
    }
}
