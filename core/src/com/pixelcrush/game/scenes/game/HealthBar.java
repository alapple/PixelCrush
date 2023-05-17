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
    private Sprite emptyHearth = new Sprite();
    private Sprite halfHearth = new Sprite();
    private Sprite fullHearth = new Sprite();
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("output/hearth.atlas"));


    public HealthBar() {
        emptyHearth = atlas.createSprite("hearth-empty");
        halfHearth = atlas.createSprite("hearth-half");
        fullHearth = atlas.createSprite("hearth-full");
    }

    public ArrayList<Image> getImages() {
        ArrayList<Image> images = new ArrayList<>();
        Sprite[] hearthsSprites = getHearthsSprites();
        for (int i = 0; i < hearthsSprites.length; i++) {
            Sprite sprite = hearthsSprites[i];
            Image img = new Image(sprite);
            img.setScaling(Scaling.contain);
            img.scaleBy(4f);
            img.setPosition(i == 0 ? 10 : img.getWidth() * 4 + 25, 10);
            images.add(img);
        }

        return images;
    }

    public Sprite[] getHearthsSprites() {
        Sprite[] hearts = new Sprite[maxHealth];

        for (int i = 0; i < Math.floor(health / 2f); i++) {
            hearts[i] = fullHearth;
        }
        if (health % 2 != 0) hearts[(int) Math.ceil(health / 2f)] = halfHearth;
        for (int i = 0; i < hearts.length; i++) {
            if (hearts[i] == null) hearts[i] = fullHearth;
        }
        return hearts;
    }
}
