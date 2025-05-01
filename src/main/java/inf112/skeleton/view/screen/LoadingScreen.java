package inf112.skeleton.view.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import inf112.skeleton.model.GamePanel;

public class LoadingScreen extends AbstractScreen   {
    

    private final AssetManager assetManager;
    

    public LoadingScreen(GamePanel context) {
        super(context); // this.context = context;
        this.assetManager = context.getAssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        // Maps
        assetManager.load("map/testMap/bossMap.tmx", TiledMap.class);
        assetManager.load("map/SampleMap/startMap.tmx", TiledMap.class);
        assetManager.load("map/SecondMap/SecondMap.tmx", TiledMap.class);

        // Items and doors
        assetManager.load("map/Door1.png", Texture.class);

        assetManager.load("Entities/Items/Key.png", Texture.class);
        assetManager.load("Entities/Items/Health.png", Texture.class);
        assetManager.load("Entities/Items/Mana.png", Texture.class);

        assetManager.load("Entities/Items/OverworldSword.png", Texture.class);
        assetManager.load("Entities/Sword/Common.png", Texture.class);
        assetManager.load("Entities/Sword/Uncommon.png", Texture.class);
        assetManager.load("Entities/Sword/Rare.png", Texture.class);

        //UI
        assetManager.load("map/miniMap.png", Texture.class);
        assetManager.load("Ui/sword_circle.png", Texture.class);
        assetManager.load("Ui/slot.png", Texture.class);
        assetManager.load("Ui/slotselected.png", Texture.class);
        assetManager.load("Ui/redtexture.png", Texture.class);
        assetManager.load("Ui/graytexture.png", Texture.class);

        //Main menu
        assetManager.load("Background/transparent.png", Texture.class);
        assetManager.load("Background/background.png", Texture.class);

        
        assetManager.finishLoading();
    }
    
    @Override
    public void render(float delta) { 

        if (assetManager.update()) {
            context.setScreen(ScreenType.MAIN_MENU);
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