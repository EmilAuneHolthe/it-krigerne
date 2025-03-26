package inf112.skeleton.view.screen;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.gameKeys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;

public class LoadingScreen extends AbstractScreen   {
    

    private final AssetManager assetManager;
    private final SpriteBatch spriteBatch;

    public LoadingScreen(GamePanel context) {
        super(context); // this.context = context;
        this.assetManager = context.getAssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("map/testMap/testMap.tmx", TiledMap.class);
        assetManager.load("map/SampleMap/samplemap.tmx", TiledMap.class);
        this.spriteBatch = context.getSpriteBatch();       
    }
    
    @Override
    public void render(float delta) { 

        if (assetManager.update()) {
            context.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {}
}