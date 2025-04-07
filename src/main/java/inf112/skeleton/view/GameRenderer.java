package inf112.skeleton.view;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import java.util.EnumMap;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.Player;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.view.ui.PlayerHUD;

public class GameRenderer implements Disposable, MapListener {

    public static final String TAG = GameRenderer.class.getSimpleName();
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch spriteBatch;
    private final AssetManager assetManager;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GLProfiler profiler;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;
    private final Stage uiStage;
    private PlayerHUD playerHUD;
    private Player player;
    private final Texture healthTexture;
    private final Texture backgroundTexture;
    private boolean showDebug = false;
    private final EnumMap<AnimationTypes, Animation<Sprite>> animationChache;

    public GameRenderer(final GamePanel context) {
        assetManager = context.getAssetManager();
        viewport = context.getViewport();
        camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();
        player = context.getPlayer();
        animationChache = new EnumMap<AnimationTypes, Animation<Sprite>>(AnimationTypes.class);

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, spriteBatch);
        context.getMapManager().addListener(this);

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        box2DDebugRenderer = new Box2DDebugRenderer();
        world = context.getWorld();

        // UI setup with ScreenViewport for fixed UI elements
        uiStage = new Stage(new ScreenViewport(), spriteBatch);
        healthTexture = new Texture(Gdx.files.internal("redtexture.png"));
        backgroundTexture = new Texture(Gdx.files.internal("graytexture.png"));
        createPlayerHUD();
    }

    private void createPlayerHUD() {
        if (playerHUD != null) {
            // Clear existing HUD
            uiStage.clear();
        }
        if (player != null) {
            playerHUD = new PlayerHUD(uiStage, player, healthTexture, backgroundTexture);
        }
    }

    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera position to follow player
        if (player != null && player.getBody() != null) {
            camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
            camera.update();
        }

        // Apply game viewport and render game elements
        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        // Render map
        if (mapRenderer.getMap() != null) {
            mapRenderer.setView(camera);
            mapRenderer.render();
        }

        // Render Box2D debug only when explicitly enabled
        if (showDebug) {
            box2DDebugRenderer.render(world, camera.combined);
        }

        // Render player
        if (player != null) {
            player.render(spriteBatch);
        }

        // Render UI with fixed position
        uiStage.getViewport().apply();
        uiStage.act(delta);
        uiStage.draw();
        if (playerHUD != null) {
            playerHUD.update();
        }

        // Debug info
        if (profiler.isEnabled()) {
            Gdx.app.debug(TAG, "Bindings: " + profiler.getTextureBindings());
            Gdx.app.debug(TAG, "Draw calls: " + profiler.getDrawCalls());
            profiler.reset();
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height, true);
    }

    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public void updatePlayer(Player player) {
        this.player = player;
        createPlayerHUD();
    }

    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        mapRenderer.dispose();
        uiStage.dispose();
        healthTexture.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void mapChanged(Map map) {
        mapRenderer.setMap(map.getTiledMap());
    }
}
