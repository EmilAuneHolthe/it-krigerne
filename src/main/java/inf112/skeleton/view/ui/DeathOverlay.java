package inf112.skeleton.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.skeleton.model.GamePanel;

/**
 * Represents a death overlay that appears when the player dies.
 * Displays a semi-transparent black background with death text and instructions.
 */
public class DeathOverlay {
    private final Stage stage;
    private final Texture blurTexture;
    private boolean isVisible;
    private final Label deathText;
    private final Label instructionText;

    /**
     * Creates a new DeathOverlay instance.
     * Initializes the stage, blur texture, and UI elements.
     * 
     * @param context The game panel context
     */
    public DeathOverlay() {
        this.stage = new Stage(new ScreenViewport());
        
        // Create a semi-transparent black texture for the blur effect
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.85f); // Increased opacity for stronger blur effect
        pixmap.fill();
        blurTexture = new Texture(pixmap);
        pixmap.dispose();

        // Create death text
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.5f); // Increased font size
        deathText = new Label("YOU DIED", new Label.LabelStyle(font, Color.RED));
        deathText.setAlignment(Align.center);

        // Create instruction text
        BitmapFont instructionFont = new BitmapFont();
        instructionFont.getData().setScale(1.2f);
        instructionText = new Label("Press ESCAPE to quit the game, when dead", new Label.LabelStyle(instructionFont, Color.WHITE));
        instructionText.setAlignment(Align.center);

        // Layout
        Table table = new Table();
        table.setFillParent(true);
        table.add(deathText).padBottom(30).row();
        table.add(instructionText);
        stage.addActor(table);

        isVisible = false;
    }

    /**
     * Shows the death overlay.
     * Makes the overlay visible and interactive.
     */
    public void show() {
        isVisible = true;
    }

    /**
     * Hides the death overlay.
     * Clears the stage and resets any persistent state.
     */
    public void hide() {
        isVisible = false;
        // Reset any state that might persist
        stage.clear();
        stage.getRoot().clear();
    }

    /**
     * Renders the death overlay if it is visible.
     * Draws the blur effect and UI elements.
     * 
     * @param batch The SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        // Draw blur effect
        batch.begin();
        batch.setColor(1, 1, 1, 0.85f); // Increased opacity for stronger blur effect
        batch.draw(blurTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);
        batch.end();
        
        // Draw UI
        stage.act();
        stage.draw();
    }

    /**
     * Updates the viewport size when the window is resized.
     * 
     * @param width The new width of the window
     * @param height The new height of the window
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Disposes of all resources used by the death overlay.
     * Should be called when the overlay is no longer needed to prevent memory leaks.
     */
    public void dispose() {
        stage.dispose();
        blurTexture.dispose();
    }
} 