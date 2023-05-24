package com.pixelcrush.game.scenes.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.scenes.game.GameScene;

public class Enemy {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private Vector2 position = new Vector2();
    public SerializedEnemy data;
    private Circle playerDetectionBounds;
    private Circle startAttackBounds;
    private TextureAtlas atlas;
    private Sprite sprite;
    public float speedModifier = 0;
    private float timeSinceLastDamage = 0;
    private int id = -1;

    public Circle getStartAttackBounds() {
        return startAttackBounds;
    }

    public Circle getPlayerDetectionBounds() {
        return playerDetectionBounds;
    }

    public Enemy(SerializedEnemy data) {
        this.data = data;
        atlas = new TextureAtlas(Gdx.files.internal(data.textureAtlasPath));
        sprite = atlas.createSprite("0");
        sprite.setSize(.5f, 1);

        playerDetectionBounds = new Circle(position, data.followRadius);
        startAttackBounds = new Circle(position, data.stopRadius);
    }

    public void spawn(int id) {
        if (this.id != -1) throw new RuntimeException("Enemy already spawned!");
        this.id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void updatePosition(float delta) {
        timeSinceLastDamage += delta;
        float velocity = (data.speed + speedModifier) * delta;

        Rectangle playerBounds = GameScene.player.getPlayerBounds();
        if (Intersector.overlaps(startAttackBounds, playerBounds) && timeSinceLastDamage > 3) {
            timeSinceLastDamage -= 3f;
            damagePlayer();
        } else if (Intersector.overlaps(playerDetectionBounds, playerBounds)) {
            Vector2 enemyPos = new Vector2(position);
            Vector2 playerPos = new Vector2(GameScene.player.position);
            Vector2 direction = new Vector2();

            direction.x = (playerPos.x + 40) - (enemyPos.x + 40);
            direction.y = (playerPos.y + 40) - (enemyPos.y + 40);
            direction.nor();

            position.x += direction.x * velocity;
            position.y += direction.y * velocity;
        }

        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(position.x, position.y);
        playerDetectionBounds.setPosition(position);
        startAttackBounds.setPosition(position);
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    public void damagePlayer() {
        GameScene.player.healthBar.damage(data.damage);
        System.out.printf("[Enemy: %d] Damaging player by %.0f; player health is now %d%n", id, data.damage, GameScene.player.healthBar.getHealth());
    }
}
