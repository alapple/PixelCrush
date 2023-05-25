package com.pixelcrush.game.scenes.game.arrows;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class StandardArrow extends SerializedArrow {
    public SerializedArrow data;
    private Vector2 position = new Vector2();
    private Sprite sprite;
    private Texture texture;


    public StandardArrow(SerializedArrow data) {
        this.data = data;
    }

    public void shoot(float delta) {

    }

}
