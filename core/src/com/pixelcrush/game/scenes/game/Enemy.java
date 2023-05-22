package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.scenes.game.enemy.SerializedEnemy;

public class Enemy {
    private Vector2 position = new Vector2();
    private SerializedEnemy enemyData;
    private Player player;
    private Circle detectionCircle;

    public Enemy(Player player, SerializedEnemy enemyData) {
        detectionCircle = new Circle(position, enemyData.followRadius);
        this.player = player;
        this.enemyData = enemyData;
    }


    public void followPlayer(float delta) {
        float velocity = enemyData.speed * delta;

        if (Intersector.overlaps(detectionCircle, player.getPlayerBounds())) {
            Vector2 enemyPos = new Vector2(position);
            Vector2 playerPos = new Vector2(player.position);
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
