package inf112.skeleton.view.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import inf112.skeleton.model.GamePanel;

public class LoadingScreen extends AbstractScreen   {
    

    private final AssetManager assetManager;
    

    public LoadingScreen(GamePanel context) {
        super(context); // this.context = context;
        this.assetManager = context.getAssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("map/testMap/testMap.tmx", TiledMap.class);
        assetManager.load("map/SampleMap/samplemap.tmx", TiledMap.class);
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