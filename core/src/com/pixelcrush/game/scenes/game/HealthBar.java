package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.pixelcrush.game.PixelCrushCore;

import java.util.ArrayList;

public class HealthBar {
    private final int maxHealth = 10;
    private final int health = maxHealth;
    private final TextureAtlas atlas = PixelCrushCore.manager.get("output/heart.atlas");
    private Sprite emptyHeart = new Sprite();
    private Sprite halfHeart = new Sprite();
    private Sprite fullHeart = new Sprite();


    public HealthBar() {
        emptyHeart = atlas.createSprite("heart-empty");
        halfHeart = atlas.createSprite("heart-half");
        fullHeart = atlas.createSprite("heart-full");
    }

    public ArrayList<Image> getImages() {
        ArrayList<Image> images = new ArrayList<>();
        ArrayList<Sprite> heartsSprites = getHeartsSprites();

        float lastX = 10;
        for (int i = 0; i < heartsSprites.size(); i++) {
            Sprite sprite = heartsSprites.get(i);
            Image img = new Image(sprite);

            img.setScaling(Scaling.contain);
            img.scaleBy(4f);
            // img.getWidth() * 4 * i + 25 + i * 6
            img.setPosition(i == 0 ? lastX : lastX + img.getWidth() + 40, 10);

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
