package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;

import java.util.Objects;

public class SettingScreen extends AbstractScreen {
    public static final Texture backgroundTexture = new Texture(Gdx.files.internal("Background/settings.png"));
    private final Stage stage;

    public SettingScreen(GamePanel context) {
        super(context);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createUI();
            }
        
    private void createUI() {
        Image background = new Image(new TextureRegionDrawable(backgroundTexture));
        background.setFillParent(true);
        stage.addActor(background);
    }
        
    
    @Override
    public void show() {
        super.show();
        keyHandler.addListener(this);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, keyHandler));
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
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
        keyHandler.removeListener(this);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
    
    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        returnToMainMenu(keyHandler, key);
    }

    private void returnToMainMenu(KeyHandler keyHandler, Keys key) {
        if (Objects.requireNonNull(key) == Keys.QUIT) {
            audioHandler.playAudio(AudioTypes.SELECT);
            System.out.println("Returning to main menu...");
            dispose();
            context.setScreen(new MainMenuScreen(context));
        }
}
}

