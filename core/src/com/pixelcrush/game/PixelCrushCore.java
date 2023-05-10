package com.pixelcrush.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pixelcrush.game.scenes.game.GameScene;

public class PixelCrushCore extends Game {
	public static PixelCrushCore INSTANCE;

	public PixelCrushCore(){
		INSTANCE = this;
	}
	@Override
	public void create () {
		setScreen(new GameScene());
	}
}
