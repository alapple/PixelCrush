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
    private Vector2 position = new Vector2();
    public SerializedEnemy data;
    private Circle playerDetectionBounds;
    private Circle startAttackBounds;
    private TextureAtlas atlas;
    private Sprite sprite;

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

    public void spawn() {
        // TODO: SPAWN ENEMY
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void updatePosition(float delta) {
        float velocity = data.speed * delta;

        Rectangle playerBounds = GameScene.player.getPlayerBounds();
        if (Intersector.overlaps(playerDetectionBounds, playerBounds) && !Intersector.overlaps(startAttackBounds, playerBounds)) {
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
}
