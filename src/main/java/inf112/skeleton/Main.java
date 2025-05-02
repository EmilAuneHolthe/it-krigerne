package inf112.skeleton;


import org.lwjgl.system.Configuration;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import inf112.skeleton.model.GamePanel;

import com.badlogic.gdx.utils.Os;

/**
 * The Main class serves as the entry point for the application.
 * It configures and launches the game using the LWJGL3 backend.
 */
public class Main {
	/**
     * The main method initializes the application configuration and starts the game.
     *
     * @param args Command-line arguments (not used in this application).
     */
	public static void main(String[] args) {
		if (SharedLibraryLoader.os == Os.MacOsX) {
			Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
		}
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("The Invisible Stairs");
		cfg.setWindowedMode(960, 540);

		new Lwjgl3Application(new GamePanel(), cfg);
	}
}
