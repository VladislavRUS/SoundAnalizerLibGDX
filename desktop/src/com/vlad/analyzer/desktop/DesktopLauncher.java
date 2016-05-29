package com.vlad.analyzer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vlad.analyzer.views.StartProgram;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "s19955991s@gmail.com");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(new StartProgram(), config);
	}
}
