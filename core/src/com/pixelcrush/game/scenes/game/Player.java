package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private static final float walkSpeed = 100;
    private static final float runSpeed = 120;
    public Sprite sprite = new Sprite();
    public Vector2 position = new Vector2(0, 0);
    private TextureAtlas atlas;

    public Player() {
        atlas = new TextureAtlas(Gdx.files.internal("output/player.atlas"));
    }

    public void handleInput(float delta) {
        float velocity = (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? runSpeed : walkSpeed) * delta;
        boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean shiftPressed = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);

        //sprinting
        if ((wPressed && dPressed && shiftPressed) || (wPressed && aPressed && shiftPressed) || (sPressed && dPressed && shiftPressed) || (sPressed && aPressed && shiftPressed)){
            position.y += velocity / 2 * (sPressed ? -1 : 1) * 1.5;
            position.x += velocity / 2 * (aPressed ? -1 : 1) * 1.5;
        }else if ((shiftPressed && wPressed)){
            sprite.setRegion(atlas.findRegion("up"));
            position.y += velocity * 1.5;
        } else if (shiftPressed && sPressed) {
            sprite.setRegion(atlas.findRegion("down"));
            position.y -= velocity * 1.5;
        } else if (shiftPressed && dPressed) {
            sprite.setRegion(atlas.findRegion("right"));
            position.x += velocity * 1.5;
        } else if (shiftPressed && aPressed) {
            sprite.setRegion(atlas.findRegion("left"));
            position.x -= velocity * 1.5;
            //Walking
        }else if ((wPressed && dPressed) || (wPressed && aPressed) || (sPressed && dPressed) || (sPressed && aPressed)) {
            position.y += velocity / 2 * (sPressed ? -1 : 1);
            position.x += velocity / 2 * (aPressed ? -1 : 1);
        } else if (wPressed) {
            sprite.setRegion(atlas.findRegion("up"));
            position.y += velocity;
        } else if (sPressed) {
            sprite.setRegion(atlas.findRegion("down"));
            position.y -= velocity;
        } else if (aPressed) {
            sprite.setRegion(atlas.findRegion("left"));
            position.x -= velocity;
        } else if (dPressed) {
            sprite.setRegion(atlas.findRegion("right"));
            position.x += velocity;
        }else sprite = atlas.createSprite("idle");



        sprite.setPosition(position.x, position.y);
    }
}
