package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.pixelcrush.game.PixelCrushCore;

import java.util.ArrayList;

public class HealthManager {
    private final int maxHealth = 10;
    private final TextureAtlas atlas = PixelCrushCore.manager.get("output/heart.atlas");
    private int health = maxHealth;
    private Sprite emptyHeart = new Sprite();
    private Sprite halfHeart = new Sprite();
    private Sprite fullHeart = new Sprite();


    public HealthManager() {
        emptyHeart = atlas.createSprite("heart-empty");
        halfHeart = atlas.createSprite("heart-half");
        fullHeart = atlas.createSprite("heart-full");
    }

    public int getHealth() {
        return health;
    }

    public void damage(float amount) {
        health -= amount;
        if (health <= 0) {
            GameScene.player.die();
        }
    }

    public ArrayList<Image> getImages() {
        ArrayList<Image> images = new ArrayList<>();
        Sprite[] heartsSprites = getHeartsSprites();

        float lastX = 10;
        for (int i = 0; i < heartsSprites.length; i++) {
            Sprite sprite = heartsSprites[i];
            Image img = new Image(sprite);

            img.setScaling(Scaling.contain);
            img.scaleBy(3.7f);
            img.setPosition(i == 0 ? lastX : lastX + img.getWidth() + 35, 10);

            images.add(img);

            lastX += img.getX() - lastX;
        }

        System.out.println(this);
        return images;
    }

    public Sprite[] getHeartsSprites() {
        float downScaledNum = (maxHealth / 2f) * health / 10f;
        Sprite[] hearts = new Sprite[(int) (maxHealth / 2)];
        int heartsPointer = 0;

        for (int i = 0; i < Math.floor(downScaledNum); i++) {
            hearts[heartsPointer] = fullHeart;
            heartsPointer++;
        }
        if (downScaledNum % 1 != 0) hearts[heartsPointer] = halfHeart;
        for (int i = 0; i < hearts.length; i++) if (hearts[i] == null) hearts[i] = emptyHeart;
        return hearts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthManager{");
        sb.append("maxHealth=").append(maxHealth);
        sb.append(", health=").append(health);
        sb.append(", hearts=[");
        Sprite[] heartsSprites = getHeartsSprites();
        for (int i = 0; i < heartsSprites.length; i++) {
            Sprite heart = heartsSprites[i];
            sb.append(heart.getTexture().toString()).append(i == heartsSprites.length - 1 ? "" : ", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}
