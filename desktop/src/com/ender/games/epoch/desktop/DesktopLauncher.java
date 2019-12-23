package com.ender.games.epoch.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ender.games.epoch.Epochkt;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Epoch";
		config.width = 1920;
		config.height = 1080;
		config.samples = 16;
		config.vSyncEnabled = true;
		config.foregroundFPS = 60;
		config.useGL30 = true;
		config.fullscreen = true;
		new LwjglApplication(new Epochkt(config.width, config.height, true), config);
	}
}