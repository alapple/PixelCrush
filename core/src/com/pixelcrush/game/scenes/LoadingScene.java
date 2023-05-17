package com.pixelcrush.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.GameScene;

import java.io.FileNotFoundException;

public class LoadingScene extends ScreenAdapter {
    private final SpriteBatch batch = new SpriteBatch();
    private final VideoPlayer videoPlayer = VideoPlayerCreator.createVideoPlayer();
    private boolean videoCompleted = false;
    private TiledMap map;

    public LoadingScene() {
        PixelCrushCore.manager.load("output/player.atlas", TextureAtlas.class);
        PixelCrushCore.manager.load("output/heart.atlas", TextureAtlas.class);
        PixelCrushCore.manager.load("output/heart.atlas", TextureAtlas.class);
        PixelCrushCore.manager.load("data/uiskin.json", Skin.class);
        map = new TmxMapLoader().load("assets/other/program-files/tiled-project/untitled.tmx");

        videoPlayer.setOnCompletionListener(file -> videoCompleted = true);
        try {
            videoPlayer.play(Gdx.files.internal("other/loading/anim.ogv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (PixelCrushCore.manager.update() && videoCompleted) {
            PixelCrushCore.INSTANCE.setScreen(new GameScene(map));
            return;
        }

        videoPlayer.update();

        Texture frame = videoPlayer.getTexture();
        if (frame != null) {
            batch.begin();
            int h = Gdx.graphics.getHeight();
            int w = Gdx.graphics.getWidth();

            if (h < w) h = (9 * w) / 16;
            else w = (16 * h) / 9;

            batch.draw(frame, 0, (Gdx.graphics.getHeight() - h) / 2f, w, h);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }
}
