package inf112.skeleton.view;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import javax.swing.Box;

import org.lwjgl.opengl.GL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;

public class GameRenderer implements Disposable, MapListener {

    public static final String TAG = GameRenderer.class.getSimpleName();
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch spriteBatch;
    private final AssetManager assetManager;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private Sprite dummySprite;

    private final GLProfiler profiler;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;

    public GameRenderer(final GamePanel context) {
        assetManager = context.getAssetManager();
        viewport = context.getViewport();
        camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, spriteBatch);
        context.getMapManager().addListener(this);

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        if(profiler.isEnabled()) {
            box2DDebugRenderer = new Box2DDebugRenderer();
            world = context.getWorld();
        } else {
            box2DDebugRenderer = null;
            world = null;
            
        }
    }
    public void render(final float alpha) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        if(mapRenderer.getMap() != null) {
            mapRenderer.setView(camera);
            mapRenderer.render();
        }

        if(profiler.isEnabled()) {
            Gdx.app.debug(TAG, "Bindings: " + profiler.getTextureBindings());
            Gdx.app.debug(TAG, "Draw calls: " + profiler.getDrawCalls());
            profiler.reset();

            box2DDebugRenderer.render(world, camera.combined);
        }
    }

    @Override
    public void dispose() {
        if (box2DDebugRenderer != null) {
            box2DDebugRenderer.dispose();
        }
        mapRenderer.dispose();
    }
    @Override
    public void mapChanged(Map map) {
        mapRenderer.setMap(map.getTiledMap());

        if(dummySprite == null) {
            dummySprite = assetManager.get("map/SampleMap/soilderWalkingAnimation.tsx",  TextureAtlas.class).createSprite("fnt");
            dummySprite.setOriginCenter();
    }
    

}
}
