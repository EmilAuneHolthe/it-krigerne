package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

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

    public MainMenuScreen(GamePanel context) {
        super(context);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createUI();
    }
    
    private void createUI() {
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);
        
        
        Texture blankTexture = new Texture(Gdx.files.internal("transparent.png")); // A fully transparent 1x1 PNG
        TextureRegionDrawable blankDrawable = new TextureRegionDrawable(blankTexture);
        
        startButton = new ImageButton(blankDrawable);
        optionsButton = new ImageButton(blankDrawable);
        exitButton = new ImageButton(blankDrawable);
        
        startButton.setBounds(385, 340, 200, 80);  
        optionsButton.setBounds(385, 235, 200, 80);
        exitButton.setBounds(385, 130, 200, 80);
        
        // Button Click Listeners
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
        
        stage.addActor(startButton);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);
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
        System.err.println("MainMeny pressed: " + key);
    }
    
    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
    }
}

