package inf112.skeleton.view.screen;

import static inf112.skeleton.model.GamePanel.assetManager;

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

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;

/**
 * Represents the main menu screen of the game.
 * Displays buttons for starting the game, accessing settings, and exiting the game.
 */
public class MainMenuScreen extends AbstractScreen {
    private final Texture backgroundTexture = assetManager.get("Background/background.png");
    private final Texture blankTexture = assetManager.get("Background/transparent.png");
    private final Stage stage;

    /**
     * Constructs a MainMenuScreen instance.
     *
     * @param context The game context, providing access to shared resources.
     */
    public MainMenuScreen(GamePanel context) {
        super(context);
        stage = new Stage(new FitViewport(ORIGINALWIDTH, ORIGINALHEIGHT));
        Gdx.input.setInputProcessor(stage);
        createUI(); 
    }
    
    private void createUI() {
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);
        
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);

        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(blankTexture);
        
        ImageButton optionsButton = new ImageButton(buttonDrawable);
        ImageButton startButton = new ImageButton(buttonDrawable);
        ImageButton exitButton = new ImageButton(buttonDrawable);
        
        // Set button sizes relative to the original window size
        float buttonWidth = ORIGINALWIDTH * 0.208f; // 200/960 ≈ 0.208
        float buttonHeight = ORIGINALHEIGHT * 0.148f; // 80/540 ≈ 0.148
        
        buttonTable.add(startButton).size(buttonWidth, buttonHeight).padBottom(ORIGINALHEIGHT * 0.046f).row(); // 25/540 ≈ 0.046
        buttonTable.add(optionsButton).size(buttonWidth, buttonHeight).padBottom(ORIGINALHEIGHT * 0.046f).row();
        buttonTable.add(exitButton).size(buttonWidth, buttonHeight);
        
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.resetPlayer();
                context.setScreen(ScreenType.GAME);
            }
        });
        
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audioHandler.playAudio(AudioTypes.SELECT);
                context.setScreen(ScreenType.SETTINGS);
            }
        });
        
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

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
        stage.clear();
    }
    
    @Override
    public void show() {
        keyHandler.addListener(this);
        audioHandler.playAudio(AudioTypes.INTRO);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, keyHandler));
    }
}

