package com.pixelcrush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(30);
		config.setForegroundFPS(60);
		config.setTitle("Of Archery And Skeletons");
		config.useVsync(true);
		new Lwjgl3Application(new PixelCrushCore(), config);
	}
}
