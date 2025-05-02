package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;

/**
 * Represents the victory screen of the game.
 * Displays a victory background and a popup image for achievements.
 * Plays victory music and handles the display of UI elements.
 */
public class VictoryScreen extends AbstractScreen {
    private final Stage stage;
    private final Texture victoryTexture;
    private final Image victoryImage;

    private final Texture popupTexture;
    private final Image popupImage;

    private float elapsedTime = 0f;
    private final float popupDelay = 1f;          // Delay before popup appears (1 second)
    private final float popupDuration = 3f;       // Popup stays visible for 3 seconds after delay

    /**
     * Constructs a VictoryScreen instance.
     *
     * @param context The game context, providing access to shared resources.
     */
    public VictoryScreen(GamePanel context) {
        super(context);
        stage = new Stage(new FitViewport(ORIGINALWIDTH, ORIGINALHEIGHT));

        // Victory background (always visible)
        victoryTexture = new Texture(Gdx.files.internal("Background/victory.png"));
        victoryImage = new Image(victoryTexture);
        victoryImage.setFillParent(true);
        stage.addActor(victoryImage);

        // Popup image (achievement)
        popupTexture = new Texture(Gdx.files.internal("Background/TopG.png"));
        popupImage = new Image(popupTexture);

        // Make the popup smaller
        float scale = 0.2f; // 50% size
        popupImage.setSize(popupTexture.getWidth() * scale, popupTexture.getHeight() * scale);

        // Position the popup (for example, bottom-left corner)
        popupImage.setPosition(340, -20); 

        // Initially invisible
        popupImage.setVisible(false);

        // Add popup on top of the background
        stage.addActor(popupImage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        audioHandler.stopMusic();
        audioHandler.playAudio(AudioTypes.VICTORY);
        audioHandler.playAudio(AudioTypes.ACHIEVMENT);
        elapsedTime = 0f;
        popupImage.setVisible(false); // Reset popup visibility
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        // Show popup after delay
        popupImage.setVisible(elapsedTime >= popupDelay && elapsedTime <= popupDelay + popupDuration);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        super.hide();
        audioHandler.stopMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
        victoryTexture.dispose();
        popupTexture.dispose();
    }
}
