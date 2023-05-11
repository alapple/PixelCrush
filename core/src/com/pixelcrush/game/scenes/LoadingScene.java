package com.pixelcrush.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.pixelcrush.game.PixelCrushCore;
import com.pixelcrush.game.scenes.game.GameScene;

import java.io.FileNotFoundException;

public class LoadingScene extends ScreenAdapter {
    private SpriteBatch batch = new SpriteBatch();
    private VideoPlayer videoPlayer = VideoPlayerCreator.createVideoPlayer();

    public LoadingScene() {
        videoPlayer.setOnCompletionListener(file -> {
            if (PixelCrushCore.manager.update()) {
                PixelCrushCore.INSTANCE.setScreen(new GameScene());
            }
        });

        try {
            videoPlayer.play(Gdx.files.internal("loading/anim.ogv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

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
