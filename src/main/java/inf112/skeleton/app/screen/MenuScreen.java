package inf112.skeleton.app.screen;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/*
 *  Ved merge:
 *   1. løs konflikter
 *   2. git add .
 *   3. git commit -m "Merge"
 *   4. git push
 * 
 *  join echo sin slack
 *  spør Kristian Rosland om hjelp, han er veldig flink
 * 
 *  
 */


import inf112.skeleton.app.GamePanel;

public class MenuScreen implements Screen {
    private final GamePanel game;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public MenuScreen(GamePanel game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // Adjust screen size

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        // Load the skin safely
        try {
            skin = new Skin(Gdx.files.internal("core/assets/neutralizer-ui.json"));
            System.out.println("✅ Skin loaded successfully!");
        } catch (Exception e) {
            System.out.println("❌ ERROR: Failed to load skin!");
            e.printStackTrace();
        }

        Label titleLabel = new Label("My Awesome Game", skin, "title");  // "title" should match a style in your JSON
        titleLabel.setFontScale(2); // Increase size
        titleLabel.setAlignment(Align.center);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(titleLabel).padBottom(20).center();
        table.row();

        // Create buttons
        TextButton startButton = new TextButton("Start Game", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add button listeners
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new GameScreen(game)); // Switch to GameScreen
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the game
            }
        });

        // Add buttons to table
        table.add(startButton).fillX().pad(10);
        table.row();
        table.add(optionsButton).fillX().pad(10);
        table.row();
        table.add(exitButton).fillX().pad(10);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (skin != null) {
            skin.dispose();
        }
        if (stage != null) {
            stage.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }
}
