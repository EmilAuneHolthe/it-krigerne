package inf112.skeleton.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.skeleton.app.GamePanel;

public class SettingScreen extends AbstractScreen {
    public static Texture backgroundTexture = new Texture(Gdx.files.internal("settings.png"));
    private Stage stage;

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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        viewport.apply(true);
        
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("Returning to main menu...");
            dispose();
            context.setScreen(new MainMenuScreen(context));
        }
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
    }

    @Override
    public void dispose() {
        super.dispose();
    }
    
}
