package gbw.melange;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import gbw.melange.Application;

import java.awt.*;
import java.awt.geom.Dimension2D;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("MELANGE");

		Dimension2D screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		config.setWindowedMode((int) screenDim.getWidth(), (int) screenDim.getHeight());
		new Lwjgl3Application(new Application(), config);
	}
}
