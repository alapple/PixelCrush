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
        ArrayList<Sprite> heartsSprites = getHeartsSprites();

        float lastX = 10;
        for (int i = 0; i < heartsSprites.size(); i++) {
            Sprite sprite = heartsSprites.get(i);
            Image img = new Image(sprite);

            img.setScaling(Scaling.contain);
            img.scaleBy(3.7f);
            img.setPosition(i == 0 ? lastX : lastX + img.getWidth() + 35, 10);

            images.add(img);

            lastX += img.getX() - lastX;
        }
        return images;
    }

    public ArrayList<Sprite> getHeartsSprites() {
        ArrayList<Sprite> hearts = new ArrayList<>();

        for (int i = 0; i < Math.floor(health / 2f); i++) {
            hearts.add(fullHeart);
        }
        if (health % 2 != 0) hearts.set((int) Math.ceil(health / 2f), halfHeart);
        for (int i = 0; i < maxHealth; i++) {
            if (i + 1 < hearts.size() || i + 1 > hearts.size()) continue;
            if (hearts.get(i) == null) hearts.add(fullHeart);
        }
        return hearts;
    }
}
