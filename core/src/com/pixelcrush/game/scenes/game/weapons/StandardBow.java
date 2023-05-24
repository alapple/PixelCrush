package com.pixelcrush.game.scenes.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.Player;

public class StandardBow extends SerializedBaseBow implements BaseBow {
    public SerializedBaseBow data;
    public static final int FRAME_COLS = 10, FRAME_ROWS = 1;
    public Sprite sprite;
    public TextureAtlas atlas;
    private Animation<TextureRegion> drawBowAnimation;
    Texture walkSheet;
    float stateTime;
    TextureRegion region;
    public Vector2 position;
    Player player;


    public StandardBow(SerializedBaseBow data){
        this.data = data;
        atlas = PixelCrushCore.manager.get(textureAtlasPath);
        sprite.setRegion(atlas.findRegion("00"));
    }

    public void createAnimation(){
        walkSheet = new Texture(Gdx.files.internal("big-bow.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        drawBowAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
        stateTime = 0f;
        region = drawBowAnimation.getKeyFrame(0);
    }

    public void followPlayer(){
        sprite.setPosition(player.position.x, player.position.y);
    }
    @Override
    public void shoot() {
        boolean isRightMuseButtonPressed = Gdx.input.isButtonPressed(2);
        if (isRightMuseButtonPressed){
            createAnimation();
        }
    }
}
