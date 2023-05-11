package com.pixelcrush.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
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
        videoPlayer.setOnCompletionListener(new VideoPlayer.CompletionListener() {
            @Override
            public void onCompletionListener(FileHandle file) {
                if (PixelCrushCore.manager.update()) {
                    PixelCrushCore.INSTANCE.setScreen(new GameScene());
                }
            }
        });

        try {
            videoPlayer.play(Gdx.files.internal("loading/anim.ogv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // PixelCrushCore.manager.load("LoadingScreen/loading-screen.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        videoPlayer.update();

        Texture frame = videoPlayer.getTexture();
        if (frame != null) {
            batch.begin();
            batch.draw(frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }
    }
}
