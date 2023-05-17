package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pixelcrush.game.scenes.game.enemy.EnemyManager;

public class GameScene extends ScreenAdapter {
    private float downScaleFactor = 32f;
    private static final boolean DEBUG_RENDER = true;
    Camera camera;
    Player player;
    Stage stage;
    OrthogonalTiledMapRenderer mapRenderer;
    private ShapeRenderer debugRenderer;
    private EnemyManager enemyManager = new EnemyManager();

    public GameScene() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        TiledMap map = new TmxMapLoader().load("assets/other/program-files/tiled-project/untitled.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / downScaleFactor);

        player = new Player();
        camera = new Camera(player);
        stage = new Stage();

        debugRenderer = new ShapeRenderer();
        try {
            enemyManager.loadAllEnemies(Gdx.files.internal("data/enemies").path());
        } catch (Exception e) {
            System.err.println("cannot load enemies: ERR");
            e.printStackTrace();
        }

        player.healthBar.getImages().forEach(image -> stage.addActor(image));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        player.handleInput(delta);
        applyPlayerSpeedModifierOnPath();

        camera.camFollowPlayer();
        camera.update();
        stage.getBatch().setProjectionMatrix(camera.getCombinedMatrix());

        mapRenderer.setView(camera.getInternalCamera());
        mapRenderer.render();

        stage.getBatch().begin();
        player.sprite.draw(stage.getBatch());
        stage.getBatch().end();

        stage.draw();

        if (DEBUG_RENDER) renderDebug();
    }

    private void renderDebug() {
        debugRenderer.setProjectionMatrix(camera.getInternalCamera().combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        debugRenderer.setColor(Color.RED);
        Rectangle playerBounds = player.getPlayerBounds();
        debugRenderer.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);

        debugRenderer.setColor(Color.YELLOW);
        TiledMapTileLayer layer = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get("way");

        for (int y = 0; y <= layer.getTileHeight(); y++) {
            for (int x = 0; x <= layer.getTileWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);

                debugRenderer.setColor(Color.GREEN);

                if (cell != null) {
                    if (camera.getInternalCamera().frustum.boundsInFrustum(x + 1.5f, y + 0.5f, 0, 1, 1, 0)) {
                        Rectangle cellCollider = new Rectangle(x, y, 1, 1);

                        debugRenderer.setColor(Color.GREEN);
                        if (cellCollider.overlaps(playerBounds)) debugRenderer.setColor(Color.RED);

                        debugRenderer.rect(
                                cellCollider.x,
                                cellCollider.y,
                                cellCollider.width,
                                cellCollider.height
                        );
                    }
                }
            }
        }
        debugRenderer.end();
    }

    public void applyPlayerSpeedModifierOnPath() {
        TiledMapTileLayer layer = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get("way");

        Rectangle playerBounds = player.getPlayerBounds();
        for (int y = 0; y <= layer.getTileHeight(); y++) {
            for (int x = 0; x <= layer.getTileWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    if (camera.getInternalCamera().frustum.boundsInFrustum(x + 1.5f, y + 0.5f, 0, 1, 1, 0)) {
                        Rectangle cellCollider = new Rectangle(x, y, 1, 1);

                        if (cellCollider.overlaps(playerBounds)) {
                            player.speedModifier = 2;
                            return;
                        } else player.speedModifier = 0;
                    }
                }
            }
        }
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
