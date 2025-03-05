package inf112.skeleton.app;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import java.util.HashMap;
import java.util.Map;
import inf112.skeleton.app.screen.*; 

public class GamePanel extends Game {
    private static GamePanel instance;
    private Map<ScreenType, Screen> screenCache;

    public GamePanel() {
        instance = this;  // Store reference to itself
        screenCache = new HashMap<>();
    }

    public static GamePanel getInstance() {
        return instance;
    }

    @Override
    public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // Initialize screens once
        addScreen(ScreenType.LOADING, new LoadingScreen());
        addScreen(ScreenType.GAME, new GameScreen());

        // Start with the loading screen
        setScreen(ScreenType.LOADING);
    }

    public void addScreen(ScreenType type, Screen screen) {
        screenCache.put(type, screen);
    }

    public void setScreen(ScreenType type) {
        Screen screen = screenCache.get(type);
        if (screen != null) {
			Gdx.app.debug("GamePanel", "Setting screen: " + type);
            super.setScreen(screen);
        } else {
            throw new IllegalArgumentException("Screen not found: " + type);
        }
    }

    public void removeScreen(ScreenType type) {
        Screen screen = screenCache.remove(type);
        if (screen != null) {
            screen.dispose(); // Clean up resources
        }
    }

    @Override
    public void dispose() {
        // Properly dispose of all screens
        for (Screen screen : screenCache.values()) {
            screen.dispose();
        }
        screenCache.clear();
    }
}
