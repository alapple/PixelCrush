package com.pixelcrush.game.scenes.game.enemy;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.scenes.game.GameScene;

public class Enemy {
    private Vector2 position = new Vector2();
    private SerializedEnemy data;
    private Circle detectionCircle;

    public Enemy(SerializedEnemy data) {
        this.data = data;
        detectionCircle = new Circle(position, data.followRadius);
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
