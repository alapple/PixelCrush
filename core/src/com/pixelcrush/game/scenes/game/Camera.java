package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.pixelcrush.game.Globals;

public class Camera {
    private final OrthographicCamera camera;
    private float downScaleFactor = Globals.DOWNSCALE_FACTOR * 3;

    public Camera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / Globals.DOWNSCALE_FACTOR, Gdx.graphics.getHeight() / Globals.DOWNSCALE_FACTOR);
        camera.update();
    }

    public float getDownScaleFactor() {
        return downScaleFactor;
    }

    public OrthographicCamera getInternalCamera() {
        return camera;
    }

    public void camFollowPlayer() {
        camera.position.x = GameScene.player.position.x;
        camera.position.y = GameScene.player.position.y;
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
