package com.pixelcrush.game.scenes.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Camera{

    SpriteBatch batch;
    Player player;
    OrthographicCamera cam;
    public Camera(){
        cam = new OrthographicCamera(100, 100);
        player = new Player();
        batch = new SpriteBatch();

    }

    public void camFollowPlayer(){
        cam.position.x = player.getPositionX();
        cam.position.y = player.getPositionY();
        cam.update();
    }

    public void render(){
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }


}
