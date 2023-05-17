package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScene extends ScreenAdapter {
    Camera camera;
    Player player;
    Stage stage;
    OrthogonalTiledMapRenderer mapRenderer;

    public GameScene() {
        player = new Player();
        camera = new Camera(player);
        stage = new Stage();

        TiledMap map = new TmxMapLoader().load("assets/other/program-files/tiled-project/untitled.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1.25f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        player.handleInput(delta);

        camera.camFollowPlayer();
        camera.update();
        stage.getBatch().setProjectionMatrix(camera.getCombinedMatrix());

        mapRenderer.setView(camera.getInternalCamera());
        mapRenderer.render();

        stage.getBatch().begin();
        player.healthBar.getImages().forEach(image -> stage.addActor(image));

        player.sprite.draw(stage.getBatch());
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.handleResize(width, height);
        stage.getViewport().setWorldSize(width, height);
        mapRenderer.getViewBounds().setSize(width, height);
        stage.getViewport().update(width, height, true);
    }
}
