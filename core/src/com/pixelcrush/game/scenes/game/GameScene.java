package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pixelcrush.game.DebugConfig;
import com.pixelcrush.game.Globals;
import com.pixelcrush.game.scenes.game.enemy.Enemy;
import com.pixelcrush.game.scenes.game.enemy.EnemyManager;
import com.pixelcrush.game.utils.TerminalColors;

public class GameScene extends ScreenAdapter {
    private final Camera camera;
    public static Player player = null;
    private final Stage stage;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ShapeRenderer debugRenderer;
    private final EnemyManager enemyManager = new EnemyManager();
    private final DebugUI debugUI;

    public GameScene(TiledMap map) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

        player = new Player();
        camera = new Camera();

        ScreenViewport svp = new ScreenViewport(camera.getInternalCamera());
        svp.setUnitsPerPixel(1 / Globals.UNITS_PER_PIXEL);
        stage = new Stage(svp);
        Gdx.input.setInputProcessor(stage);

        debugRenderer = new ShapeRenderer();
        try {
            enemyManager.loadAllEnemies(Gdx.files.internal("data/enemies").path());
        } catch (Exception e) {
            System.err.println("cannot load enemies: ERR");
            e.printStackTrace();
        }

        player.healthBar.getImages().forEach(stage::addActor);

        stage.addActor(player);

        enemyManager.loadStageEnemies(new com.pixelcrush.game.scenes.game.enemy.Stage(1, 3, 10));
        enemyManager.spawnEnemies();
        debugUI = new DebugUI(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // player.handleInput(delta);
        applySpeedModifierOnPath();

        enemyManager.updatePositions(delta);

        camera.camFollowPlayer();
        // camera.update();
        // stage.getBatch().setProjectionMatrix(camera.getCombinedMatrix());

        mapRenderer.setView(camera.getInternalCamera());
        mapRenderer.render();

        stage.act(delta);
        /*stage.getBatch().begin();
        // player.sprite.draw(stage.getBatch());
        enemyManager.enemySprites.forEach(sprite -> sprite.draw(stage.getBatch()));
        stage.getBatch().end();*/

        stage.draw();
        System.out.println(stage.getCamera());
        System.out.println(TerminalColors.ANSI_RED + camera.getInternalCamera() + TerminalColors.ANSI_RESET);

        debugUI.update();
        if (DebugConfig.DEBUG_RENDER) {
            debugUI.updateFPSText();
            renderDebug();
        }
    }

    private void renderDebug() {
        debugRenderer.setProjectionMatrix(camera.getInternalCamera().combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        debugRenderer.setColor(Color.RED);
        Rectangle playerBounds = player.getPlayerBounds();
        debugRenderer.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);


        enemyManager.enemies.forEach(enemy -> {
            Circle enemyDetectionCircle = enemy.getPlayerDetectionBounds();
            debugRenderer.setColor(Color.VIOLET);
            debugRenderer.circle(enemyDetectionCircle.x, enemyDetectionCircle.y, enemyDetectionCircle.radius, 30);

            Circle enemyStopDetection = enemy.getStartAttackBounds();
            debugRenderer.setColor(Color.PURPLE);
            debugRenderer.circle(enemyStopDetection.x, enemyStopDetection.y, enemyStopDetection.radius, 30);
        });

        debugRenderer.setColor(Color.YELLOW);
        TiledMapTileLayer layer = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get("path");

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

    public void applySpeedModifierOnPath() {
        TiledMapTileLayer layer = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get("path");

        Rectangle playerBounds = player.getPlayerBounds();
        for (int y = 0; y <= layer.getTileHeight(); y++) {
            for (int x = 0; x <= layer.getTileWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    if (camera.getInternalCamera().frustum.boundsInFrustum(x + 1.5f, y + 0.5f, 0, 1, 1, 0)) {
                        Rectangle cellCollider = new Rectangle(x, y, 1, 1);

                        if (Intersector.overlaps(playerBounds, cellCollider)) {
                            player.speedModifier = 2;
                            return;
                        } else player.speedModifier = 0;

                        for (Enemy enemy : enemyManager.enemies) {
                            if (Intersector.overlaps(enemy.getStartAttackBounds(), cellCollider)) {
                                enemy.speedModifier = 2;
                            } else enemy.speedModifier = 0;
                        }
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
        debugUI.handleResize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();
        stage.dispose();
        debugRenderer.dispose();
        player.dispose();
    }
}
