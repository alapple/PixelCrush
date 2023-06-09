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
    public SerializedEnemy data;
    public float speedModifier = 0;
    private Vector2 position = new Vector2();
    private Circle playerDetectionBounds;
    private Circle startAttackBounds;
    private Circle groupFollowBounds;
    private TextureAtlas atlas;
    private Sprite sprite;
    private float timeSinceLastDamage = 0;
    private int id = -1;
    private boolean followingPlayer;

    public Enemy(SerializedEnemy data) {
        this.data = data;
        atlas = new TextureAtlas(Gdx.files.internal(data.textureAtlasPath));
        sprite = atlas.createSprite(data.textureRegions.idle);
        sprite.setSize(1, 1);

        playerDetectionBounds = new Circle(position, data.followRadius);
        startAttackBounds = new Circle(position, data.stopRadius);
        groupFollowBounds = new Circle(position, data.groupFollowCircle);
    }

    public Circle getStartAttackBounds() {
        return startAttackBounds;
    }

    public Circle getPlayerDetectionBounds() {
        return playerDetectionBounds;
    }
    public Circle getGroupFollowBounds() {
        return groupFollowBounds;
    }

    public void spawn(int id) {
        if (this.id != -1) throw new RuntimeException("Enemy already spawned!");
        this.id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isFollowingPlayer() {
        return followingPlayer;
    }

    public void updatePosition(float delta) {
        timeSinceLastDamage += delta;
        float velocity = (data.speed + speedModifier) * delta;

        Rectangle playerBounds = GameScene.player.getPlayerBounds();
        followingPlayer = Intersector.overlaps(playerDetectionBounds, playerBounds);

        boolean followingEnemyInCloseRange = false;
        for (Enemy enemy : EnemyManager.getInstance().enemies) {
            followingEnemyInCloseRange = enemy.isFollowingPlayer() && Intersector.overlaps(groupFollowBounds, enemy.getStartAttackBounds());
        }

        if (Intersector.overlaps(startAttackBounds, playerBounds)) {
            if (timeSinceLastDamage > 3) {
                timeSinceLastDamage -= 3f;
                damagePlayer();
            }
        } else if (followingPlayer || followingEnemyInCloseRange) {
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
        groupFollowBounds.setPosition(position);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    public void damagePlayer() {
        GameScene.player.healthManager.damage(data.damage);
        System.out.printf("[Enemy: %d] Damaging player by %.0f; player health is now %d%n", id, data.damage, GameScene.player.healthManager.getHealth());
    }
}
