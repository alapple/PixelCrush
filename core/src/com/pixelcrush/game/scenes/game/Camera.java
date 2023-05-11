package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Camera {
    private OrthographicCamera camera;
    private Player player;

    public Camera(Player player) {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);

        this.player = player;
    }

    public void camFollowPlayer() {
        camera.position.x = player.getPositionX();
        camera.position.y = player.getPositionY();
        camera.update();
    }

    public void update() {
        camera.update();
    }

    public void handleResize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    public Matrix4 getCombinedMatrix() {
        return camera.combined;
    }
}
