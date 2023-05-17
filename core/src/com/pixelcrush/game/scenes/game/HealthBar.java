package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;

public class HealthBar {
    private int maxHealth = 10;
    private int health = maxHealth;
    private Sprite emptyHeart = new Sprite();
    private Sprite halfHeart = new Sprite();
    private Sprite fullHeart = new Sprite();
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("output/heart.atlas"));


    public HealthBar() {
        emptyHeart = atlas.createSprite("heart-empty");
        halfHeart = atlas.createSprite("heart-half");
        fullHeart = atlas.createSprite("heart-full");
    }

    public ArrayList<Image> getImages() {
        ArrayList<Image> images = new ArrayList<>();
        Sprite[] heartsSprites = getHeartsSprites();
        for (int i = 0; i < heartsSprites.length; i++) {
            Sprite sprite = heartsSprites[i];
            Image img = new Image(sprite);
            img.setScaling(Scaling.contain);
            img.scaleBy(4f);
            img.setPosition(i == 0 ? 10 : img.getWidth() * 4 + 25, 10);
            images.add(img);
        }

        return images;
    }

    public Sprite[] getHeartsSprites() {
        Sprite[] hearts = new Sprite[maxHealth];

        for (int i = 0; i < Math.floor(health / 2f); i++) {
            hearts[i] = fullHeart;
        }
        if (health % 2 != 0) hearts[(int) Math.ceil(health / 2f)] = halfHeart;
        for (int i = 0; i < hearts.length; i++) {
            if (hearts[i] == null) hearts[i] = fullHeart;
        }
        return hearts;
    }
}
