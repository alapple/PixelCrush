package com.pixelcrush.game.scenes.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class Camera{
    private OrthographicCamera cam;
    private Player player;
    public Camera(Player player){
        cam = new OrthographicCamera(100, 100);
        cam.setToOrtho(false);

        this.player = player;
    }

    public void camFollowPlayer(){
        cam.position.x = player.getPositionX();
        cam.position.y = player.getPositionY();
        cam.update();
    }

    public void update() {
        cam.update();
    }

    public Matrix4 getCombinedMatrix(){
        return cam.combined;
    }
}
