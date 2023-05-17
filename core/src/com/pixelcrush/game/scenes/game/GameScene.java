package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class GameScene extends ScreenAdapter {
    private float downScaleFactor = 32f;
    private static final boolean DEBUG_RENDER = true;
    Camera camera;
    Player player;
    Stage stage;
    OrthogonalTiledMapRenderer mapRenderer;
    ShapeRenderer debugRenderer;

    public GameScene() {
        TiledMap map = new TmxMapLoader().load("assets/other/program-files/tiled-project/untitled.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / downScaleFactor);

        player = new Player();
        camera = new Camera(player);
        stage = new Stage();

        debugRenderer = new ShapeRenderer();
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

        if (DEBUG_RENDER) renderDebug();
    }

    private void renderDebug() {
        debugRenderer.setProjectionMatrix(camera.getInternalCamera().combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        debugRenderer.setColor(Color.RED);
        debugRenderer.rect(player.position.x, player.position.y, player.sprite.getWidth(), player.sprite.getHeight());

        Random rng = new Random();
        debugRenderer.setColor(Color.YELLOW);
        TiledMapTileLayer layer = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get("way");

        for (int y = 0; y <= layer.getTileHeight(); y++) {
            for (int x = 0; x <= layer.getTileWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);

                Color c = new Color();
                c.r = rng.nextInt(1, 255);
                c.g = rng.nextInt(1, 255);
                c.b = rng.nextInt(1, 255);
                debugRenderer.setColor(c);

                if (cell != null) {
                    if (camera.getInternalCamera().frustum.boundsInFrustum(x + 1.5f, y + 0.5f, 0, 1, 1, 0)) {
                        debugRenderer.rect(
                                x,
                                y,
                                1,
                                1
                        );
                    }
                }
            }
        }
        debugRenderer.end();
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
