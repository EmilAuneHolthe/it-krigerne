package inf112.skeleton.view.screen;

import com.badlogic.gdx.Screen;

import inf112.skeleton.view.screen.MainMenuScreen;


public enum ScreenType {
    GAME(GameScreen.class),
    LOADING(LoadingScreen.class),
    MAIN_MENU(MainMenuScreen.class),
    SETTINGS(SettingScreen.class);



    private final Class<? extends AbstractScreen> screenClass;

    ScreenType(Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends AbstractScreen> getScreenClass() {
        return screenClass;
    }
    
}
