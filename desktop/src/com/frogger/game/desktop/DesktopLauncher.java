package com.frogger.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.frogger.game.FroggerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Frogger Game";
		//config.useGL20 = true;
		config.height = FroggerGame.screenHeight;
		config.width = FroggerGame.screenWidth;
		config.resizable = false;
		new LwjglApplication(new FroggerGame(), config);
	}
}
