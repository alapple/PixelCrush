package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScene extends ScreenAdapter {

    Camera cam;
    SpriteBatch batch;
    Player player;

    public GameScene() {
        player = new Player();
        batch = new SpriteBatch();
        cam = new Camera(player);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        player.handleInput(delta);

        cam.camFollowPlayer();

        cam.update();
        batch.setProjectionMatrix(cam.getCombinedMatrix());

        batch.begin();
        batch.draw(player.playerTexture, player.position.x, player.position.y);
        batch.end();
    }
}
