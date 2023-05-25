package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.weapons.BaseBow;

public class Player extends Actor {
    public static final float WALK_SPEED = 6;
    public static final float RUN_SPEED = 12;
    private static Player INSTANCE;
    private static float walkSpeed = WALK_SPEED;
    private static float runSpeed = RUN_SPEED;
    private static float pathSpeedModifier = 2;
    public float activeSpeedModifier = 0;
    public Sprite sprite = new Sprite();
    public Vector2 position = new Vector2(0, 0);
    public HealthManager healthManager;
    public Rectangle bounds;
    public BaseBow bow;
    private TextureAtlas atlas;

    private Player() {
    }

    public synchronized static Player getInstance() {
        if (INSTANCE == null) INSTANCE = new Player();
        return INSTANCE;
    }

    public void spawn() {
        atlas = PixelCrushCore.manager.get("output/player.atlas");
        healthManager = new HealthManager();

        // handleinput has to be called once so idle is applied
        handleInput(0);
        bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void act(float delta) {
        handleInput(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }

    public void loadPlayerData() {
        try {
            String jsonString = Gdx.files.internal("data/playerData.jsonc").readString();
            SerializedPlayerData playerData = new Gson().fromJson(jsonString, SerializedPlayerData.class);
            walkSpeed = playerData.walkSpeed;
            runSpeed = playerData.runSpeed;
            pathSpeedModifier = playerData.pathSpeedModifier;
        } catch (GdxRuntimeException gre) {
            gre.printStackTrace();
            System.err.println("Unable to load data/playerData.jsonc. Please verify that the file exists and is encoded in the correct format!");
        } catch (JsonSyntaxException jse) {
            System.err.println("data/playerData.jsonc was loaded correctly but couldn't be parsed by the JSON parser. Please verify that the file content is valid JSON and contains all needed key-value pairs");
        }
    }

    public void handleInput(float delta) {
        float velocity = ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? runSpeed : walkSpeed) + activeSpeedModifier) * delta;
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

        sprite.setCenter(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(1, 1);
    }

    public void die() {
        System.out.println("Player died");
        Gdx.app.exit();
    }

    public Rectangle getPlayerBounds() {
        bounds.setPosition(position);
        return bounds;
    }

    public float getPathSpeedModifier() {
        return pathSpeedModifier;
    }

    public boolean overlapsWith(Rectangle rect) {
        return getPlayerBounds().overlaps(rect);
    }

    public void dispose() {
        atlas.dispose();
    }
}
