package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public Sprite sprite = new Sprite(new Texture("Player/character-sprite-002.png"));
    public Vector2 position = new Vector2(0,0);
    private float walkSpeed = 100;
    private float runSpeed = 120;

    public Player() {
        sprite.scale(3);
    }

    public void handleInput(float delta) {
        float velocity = (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? runSpeed : walkSpeed) * delta;
        boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);

        if ((wPressed && dPressed) || (wPressed && aPressed) || (sPressed && dPressed) || (sPressed && aPressed)) {
            position.y += velocity / 2 * (sPressed ? -1 : 1);
            position.x += velocity / 2 * (aPressed ? -1 : 1);
        }
        else if(wPressed || sPressed) position.y += velocity * (sPressed ? -1 : 1);
        else if (aPressed || dPressed) position.x += velocity * (aPressed ? -1 : 1);

        sprite.setPosition(position.x, position.y);
    }
}
