package inf112.skeleton.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import inf112.skeleton.app.GamePanel;

public class MainMenuScreen extends AbstractScreen {
    private Stage stage;
    private Texture backgroundTexture;

    public MainMenuScreen(GamePanel context) {
        super(context);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        // Load background image
        backgroundTexture = new Texture("background.jpg"); // Ensure this is in assets
        
        createUI();
    }

    private void createUI() {
        // Create background image and add to stage
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);

        // Create invisible buttons
        Texture blankTexture = new Texture("transparent.png"); // A fully transparent 1x1 PNG
        TextureRegionDrawable blankDrawable = new TextureRegionDrawable(blankTexture);

        ImageButton startButton = new ImageButton(blankDrawable);
        ImageButton optionsButton = new ImageButton(blankDrawable);
        ImageButton exitButton = new ImageButton(blankDrawable);

        // Set button positions (adjust to match text positions in your background)
        startButton.setBounds(300, 350, 200, 50);  // x, y, width, height
        optionsButton.setBounds(300, 280, 200, 50);
        exitButton.setBounds(300, 210, 200, 50);

        // Button Click Listeners
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(ScreenType.GAME);
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Navigate to Options Screen (if implemented)
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to the stage
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
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}

