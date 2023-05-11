package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScene extends ScreenAdapter {

    Camera camera;
    SpriteBatch batch;
    Player player;

    public GameScene() {
        player = new Player();
        batch = new SpriteBatch();
        camera = new Camera(player);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        player.handleInput(delta);

        camera.camFollowPlayer();

        camera.update();
        batch.setProjectionMatrix(camera.getCombinedMatrix());

        batch.begin();
        batch.draw(player.playerTexture, player.position.x, player.position.y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.handleResize(width, height);
    }
}
