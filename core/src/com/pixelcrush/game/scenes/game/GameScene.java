package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScene extends ScreenAdapter {
    Camera camera;
    Player player;
    Stage stage;

    public GameScene() {
        stage = new Stage();
        player = new Player();
        camera = new Camera(player);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        player.handleInput(delta);

        camera.camFollowPlayer();

        camera.update();
        stage.getBatch().setProjectionMatrix(camera.getCombinedMatrix());

        stage.getBatch().begin();

        player.healthBar.getImages().forEach(image -> {
            stage.addActor(image);
        });

        player.sprite.draw(stage.getBatch());
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.handleResize(width, height);
        stage.getViewport().update(width, height, true);
    }
}
