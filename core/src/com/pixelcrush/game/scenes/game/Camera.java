package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Camera {
    private float downScaleFactor = 48f;

    private OrthographicCamera camera;
    private Player player;

    public Camera(Player player) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 16f, Gdx.graphics.getHeight() / 16f);
        camera.update();
        this.player = player;
    }

    public OrthographicCamera getInternalCamera() {
        return camera;
    }

    public void camFollowPlayer() {
        camera.position.x = player.position.x;
        camera.position.y = player.position.y;
        camera.update();
    }

    public void update() {
        camera.update();
    }

    public void handleResize(int width, int height) {
        camera.viewportWidth = width / downScaleFactor;
        camera.viewportHeight = height / downScaleFactor;
        camera.update();
    }

    public Matrix4 getCombinedMatrix() {
        return camera.combined;
    }

    public void setDownScaleFactor(float downScaleFactor) {
        this.downScaleFactor = downScaleFactor;
        handleResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
