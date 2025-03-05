package inf112.skeleton.app.screen;

import com.badlogic.gdx.Screen;


public enum ScreenType {
    GAME(LoadingScreen.class),
    LOADING(GameScreen.class);

    private final Class<? extends Screen> screenClass;

    ScreenType(Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
    
}
