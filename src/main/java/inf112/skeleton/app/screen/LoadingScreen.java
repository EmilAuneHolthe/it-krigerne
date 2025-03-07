package inf112.skeleton.app.screen;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.skeleton.app.GamePanel;

public class LoadingScreen extends AbstractScreen {

    private final AssetManager assetManager;

    public LoadingScreen(GamePanel context) {
        super(context); // this.context = context;
        this. assetManager = context.getAssetManager();
        assetManager.load("src/main/resources/map/map.tmx", TiledMap.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (assetManager.update()) {
            context.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}