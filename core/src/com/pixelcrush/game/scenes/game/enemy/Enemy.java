package com.pixelcrush.game.scenes.game.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.scenes.game.GameScene;

public class Enemy {
    private Vector2 position = new Vector2();
    public SerializedEnemy data;
    private Circle detectionCircle;
    private Sprite sprite = new Sprite();

    public Enemy(SerializedEnemy data) {
        this.data = data;
        detectionCircle = new Circle(position, data.followRadius);
    }

    public void spawn() {
        // TODO: SPAWN ENEMY
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void followPlayer(float delta) {
        float velocity = data.speed * delta;

        if (Intersector.overlaps(detectionCircle, GameScene.player.getPlayerBounds())) {
            Vector2 enemyPos = new Vector2(position);
            Vector2 playerPos = new Vector2(GameScene.player.position);
            Vector2 direction = new Vector2();

            direction.x = (playerPos.x + 40) - (enemyPos.x + 40);
            direction.y = (playerPos.y + 40) - (enemyPos.y + 40);
            direction.nor();

            position.x += direction.x * velocity;
            position.y += direction.y * velocity;

            System.out.println(position);
        }
    }
}
