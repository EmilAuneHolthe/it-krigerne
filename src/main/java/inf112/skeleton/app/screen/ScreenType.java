package inf112.skeleton.app.screen;

import inf112.skeleton.app.screen.MainMenuScreen;
import com.badlogic.gdx.Screen;


public enum ScreenType {
    GAME(GameScreen.class),
    LOADING(LoadingScreen.class),
    MAIN_MENU(MainMenuScreen.class);


    private final Class<? extends AbstractScreen> screenClass;

    ScreenType(Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends AbstractScreen> getScreenClass() {
        return screenClass;
    }
    
}
