package com.pixelcrush.game.scenes.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Heart extends Actor {
    private int index;
    private Vector2 position;
    private float scale;
    public HeartState state = HeartState.EMPTY;
    public Sprite sprite = new Sprite();

    public Heart(int index, Vector2 position, float scale) {
        this.index = index;
        this.position = position;
        this.scale = scale;
    }
    public void draw(Batch batch, float alpha) {
        TextureAtlas.AtlasRegion region = GameScene.player.healthManager.getTextureRegionForState(GameScene.player.healthManager.getHeartState(index));
        sprite.setRegion(region);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(scale, scale);
        sprite.draw(batch);
    }

    @Override
    public String toString() {
        return "Heart{" + "index=" + index +
                ", position=" + position +
                ", scale=" + scale +
                ", state=" + state +
                ", sprite=" + sprite +
                '}';
    }
}
