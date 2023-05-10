package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public Texture playerTexture = new Texture("Player/character-sprite-002.png");
    public Vector2 position = new Vector2(0,0);
    private float walkSpeed = 100;
    private float runSpeed = 120;

    public float getPositionX(){
        return position.x;
    }

    public float getPositionY(){
        return position.y;
    }


    public void handleInput(float delta) {
        float velocity = walkSpeed * delta;
        boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);

        Vector2 posBefore = new Vector2(position.x, position.y);
        if ((wPressed && dPressed) || (wPressed && aPressed) || (sPressed && dPressed) || (sPressed && aPressed)) {
            position.y += velocity / 2 * (sPressed ? -1 : 1);
            position.x += velocity / 2 * (aPressed ? -1 : 1);
        }
        else if(wPressed || sPressed) position.y += velocity * (sPressed ? -1 : 1);
        else if (aPressed || dPressed) position.x += velocity * (aPressed ? -1 : 1);
    }
}
