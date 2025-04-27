package inf112.skeleton.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.view.screen.ScreenType;

public class DeathOverlay {
    private final Stage stage;
    private final GamePanel context;
    private final Texture blurTexture;
    private boolean isVisible;
    private final Label deathText;

    public DeathOverlay(GamePanel context) {
        this.context = context;
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

        // Layout
        Table table = new Table();
        table.setFillParent(true);
        table.add(deathText).padBottom(50).row();
        stage.addActor(table);

        isVisible = false;
    }

    public void show() {
        isVisible = true;
        Gdx.input.setInputProcessor(stage);
    }

    public void hide() {
        isVisible = false;
        // Reset any state that might persist
        stage.clear();
        stage.getRoot().clear();
    }

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

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        blurTexture.dispose();
    }
} 