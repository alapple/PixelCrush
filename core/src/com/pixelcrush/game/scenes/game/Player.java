package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.weapons.BaseBow;

public class Player {
    public static final float WALK_SPEED = 6;
    public static final float RUN_SPEED = 12;

    private static final float walkSpeed = WALK_SPEED;
    private static final float runSpeed = RUN_SPEED;
    private final TextureAtlas atlas;
    public float speedModifier = 0;
    public Sprite sprite = new Sprite();
    public Vector2 position = new Vector2(0, 0);
    public int health = 10;
    public HealthBar healthBar;
    public Rectangle bounds;
    public BaseBow bow;

    public Player() {
        atlas = PixelCrushCore.manager.get("output/player.atlas");
        healthBar = new HealthBar();

        // handleinput has to be called once so idle is applied
        handleInput(0);
        bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
    }

    public void handleInput(float delta) {
        float velocity = ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? runSpeed : walkSpeed) + speedModifier) * delta;
        boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);

        // Gdx.app.debug("handleInput", "velocity: %f; speedModifier: %f".formatted(velocity, speedModifier));

        if ((wPressed && dPressed) || (wPressed && aPressed) || (sPressed && dPressed) || (sPressed && aPressed)) {
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
        } else sprite = atlas.createSprite("idle");

        sprite.setPosition(position.x, position.y);
        sprite.setSize(1, 1);
    }

    public Rectangle getPlayerBounds() {
        bounds.setPosition(position);
        return bounds;
    }

    public boolean overlapsWith(Rectangle rect) {
        return getPlayerBounds().overlaps(rect);
    }

    public void dispose() {
        atlas.dispose();
    }
}
