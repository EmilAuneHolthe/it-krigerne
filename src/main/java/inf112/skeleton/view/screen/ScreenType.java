package inf112.skeleton.view.screen;

/**
 * Enum representing the different types of screens in the game.
 * Each screen type is associated with a specific class that extends {@link AbstractScreen}.
 */
public enum ScreenType {
    GAME(GameScreen.class),
    LOADING(LoadingScreen.class),
    MAIN_MENU(MainMenuScreen.class),
    SETTINGS(SettingScreen.class),
    VICTORY(VictoryScreen.class);

    private final Class<? extends AbstractScreen> screenClass;

    /**
     * Constructs a ScreenType enum value.
     *
     * @param screenClass The class associated with the screen type.
     */
    ScreenType(Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    /**
     * Gets the class associated with the screen type.
     *
     * @return The class extending {@link AbstractScreen} for this screen type.
     */
    public Class<? extends AbstractScreen> getScreenClass() {
        return screenClass;
    }
}
