package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private final Vector2 position = new Vector2(0, 0);
    private final Circle detectionRadius;
    Player player;
    private float maxVelocity;
    private int health;
    private int damage;

    public Enemy() {
        player = new Player();
        detectionRadius = new Circle(position, 2);

    }


    public void followPlayer(float delta) {
        float velocity = maxVelocity * delta;
        if (Intersector.overlaps(detectionRadius, player.getPlayerBounds())) {
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
