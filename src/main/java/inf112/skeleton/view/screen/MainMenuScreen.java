package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;

public class MainMenuScreen extends AbstractScreen {
    public static Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private Stage stage;
    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;
    private boolean debugMode = false; // Toggle for debug outlines

    public MainMenuScreen(GamePanel context) {
        super(context);
        // Use FitViewport instead of ScreenViewport to maintain aspect ratio
        stage = new Stage(new FitViewport(originalWidth, originalHeight));
        Gdx.input.setInputProcessor(stage);
        createUI();
    }
    
    private void createUI() {
        // Create and add background
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);
        
        // Create a table for button layout
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        
        // Create debug texture for button outlines
        Drawable buttonDrawable;
        if (debugMode) {
            // Create a debug texture with a visible border
            int size = 200; // Size of the texture
            Pixmap pixmap = new Pixmap(size, 80, Pixmap.Format.RGBA8888);
            
            // Fill with transparent color
            pixmap.setColor(0, 0, 0, 0);
            pixmap.fill();
            
            // Draw border with thicker lines
            pixmap.setColor(1, 1, 1, 1); // White border
            // Draw a thicker border by drawing multiple lines
            for (int i = 0; i < 3; i++) {
                pixmap.drawRectangle(i, i, size - 2*i, 80 - 2*i);
            }
            
            Texture debugTexture = new Texture(pixmap);
            pixmap.dispose();
            
            // Create a drawable with a border
            TextureRegion region = new TextureRegion(debugTexture);
            buttonDrawable = new TextureRegionDrawable(region);
        } else {
            // Use the original transparent texture
            Texture blankTexture = new Texture(Gdx.files.internal("transparent.png"));
            buttonDrawable = new TextureRegionDrawable(blankTexture);
        }
        
        // Create buttons with the appropriate drawable
        startButton = new ImageButton(buttonDrawable);
        optionsButton = new ImageButton(buttonDrawable);
        exitButton = new ImageButton(buttonDrawable);
        
        // Set button sizes relative to the original window size
        float buttonWidth = originalWidth * 0.208f; // 200/960 ≈ 0.208
        float buttonHeight = originalHeight * 0.148f; // 80/540 ≈ 0.148
        
        // Add debug borders if enabled
        if (debugMode) {
            startButton.setColor(1, 0, 0, 0.3f); // Red
            optionsButton.setColor(0, 1, 0, 0.3f); // Green
            exitButton.setColor(0, 0, 1, 0.3f); // Blue
        }
        
        // Add buttons to table with spacing
        buttonTable.add(startButton).size(buttonWidth, buttonHeight).padBottom(originalHeight * 0.046f).row(); // 25/540 ≈ 0.046
        buttonTable.add(optionsButton).size(buttonWidth, buttonHeight).padBottom(originalHeight * 0.046f).row();
        buttonTable.add(exitButton).size(buttonWidth, buttonHeight);
        
        // Add button click listeners
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audioHandler.stopMusic();
                System.out.println("Starting game...");
                context.resetPlayer();
                context.setScreen(ScreenType.LOADING);
            }
        });
        
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Going to settings...");
                audioHandler.playAudio(AudioTypes.SELECT);
                context.setScreen(ScreenType.SETTINGS);
            }
        });
        
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exiting game...");
                Gdx.app.exit();
            }
        });
        
        // Add the table to the stage
        stage.addActor(buttonTable);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
        viewport.apply(true);
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void hide() {
        keyHandler.removeListener(this);
        audioHandler.playAudio(AudioTypes.SELECT);
    }
    
    @Override
    public void show() {
        keyHandler.addListener(this);
        audioHandler.playAudio(AudioTypes.INTRO);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, keyHandler));
    }
    
    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        // Toggle debug mode with BACKSPACE key
        if (key == Keys.BACK) {
            debugMode = !debugMode;
            System.out.println("Debug mode: " + (debugMode ? "ON" : "OFF"));
            // Recreate UI to update debug visuals
            stage.clear();
            createUI();
        }
        System.err.println("MainMeny pressed: " + key);
    }
    
    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
    }
}

