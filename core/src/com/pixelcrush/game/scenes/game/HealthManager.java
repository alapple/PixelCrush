package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.PixelCrushCore;

public class HealthManager {
    private final int maxHealth = 10;
    private final TextureAtlas atlas = PixelCrushCore.manager.get("output/heart.atlas");
    private int health = maxHealth;
    private TextureAtlas.AtlasRegion emptyHeart;
    private TextureAtlas.AtlasRegion halfHeart;
    private TextureAtlas.AtlasRegion fullHeart;
    private HeartState[] heartStates = new HeartState[maxHealth / 2];

    public HealthManager() {
        emptyHeart = atlas.findRegion("heart-empty");
        halfHeart = atlas.findRegion("heart-half");
        fullHeart = atlas.findRegion("heart-full");
        updateHeartStates();
    }

    public int getHealth() {
        return health;
    }

    public void damage(float amount) {
        health -= amount;
        updateHeartStates();
        System.out.println(this);
        if (health <= 0) {
            GameScene.player.die();
        }
    }

    public Heart[] initHearts() {
        updateHeartStates();

        Vector2 position = new Vector2(0, 10);
        Heart[] hearts = new Heart[maxHealth / 2];
        for (int i = 0; i < heartStates.length; i++) {
            position.x = 40 * i + 10;
            hearts[i] = new Heart(i, new Vector2(position.x, position.y), 33);
        }

        return hearts;
    }

    public void updateHeartStates() {
        float downScaledNum = (maxHealth / 2f) * health / 10f;
        int heartsPointer = 0;

        for (int i = 0; i < Math.floor(downScaledNum); i++) {
            heartStates[heartsPointer] = HeartState.FULL;
            heartsPointer++;
        }
        if (downScaledNum % 1 != 0) {
            heartStates[heartsPointer] = HeartState.HALF;
            heartsPointer++;
        }
        for (int i = 0; i < heartStates.length; i++) {
            if (i >= heartsPointer) {
                heartStates[heartsPointer] = HeartState.EMPTY;
                heartsPointer++;
            }
        }
    }

    public TextureAtlas.AtlasRegion getTextureRegionForState(HeartState heartState) {
        if (heartState == HeartState.FULL) return fullHeart;
        else if (heartState == HeartState.HALF) return halfHeart;
        return emptyHeart;
    }

    public HeartState getHeartState(int heartIndex) {
        return heartStates[heartIndex];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthManager{");
        sb.append("maxHealth=").append(maxHealth);
        sb.append(", health=").append(health);
        sb.append(", hearts=[");
        for (int i = 0; i < heartStates.length; i++) {
            sb.append(heartStates[i]);
            if (i + 1 != heartStates.length) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}
