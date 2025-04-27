package inf112.skeleton.view;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.door.Door;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.view.ui.PlayerHUD;
import inf112.skeleton.view.ui.ItemBar;

public class GameRenderer implements Disposable, MapListener {

    public static final String TAG = GameRenderer.class.getSimpleName();
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch spriteBatch;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GLProfiler profiler;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;
    private final Stage uiStage;
    private PlayerHUD playerHUD;
    private Player player;
    private final Texture healthTexture;
    private final Texture backgroundTexture;
    private final Texture manaTexture;
    private final BitmapFont font;
    private boolean showDebug = false;
    private Array<Enemy> enemies;
    private debug debug;
    private Array<Item> items;
    private ItemBar itemBar;
    private Array<Door> doors;
    private final MapManager mapManager;
    private TaskBoard taskBoard;

    public GameRenderer(final GamePanel context) {
        viewport = context.getViewport();
        camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();
        player = context.getPlayer();
        enemies = context.getEnemy();
        items = context.getItems();
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, spriteBatch);
        context.getMapManager().addListener(this);

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        box2DDebugRenderer = new Box2DDebugRenderer();
        world = context.getWorld();
        this.mapManager = context.getMapManager();

        // UI setup with ScreenViewport for fixed UI elements
        uiStage = new Stage(new ScreenViewport(), spriteBatch);
        healthTexture = new Texture(Gdx.files.internal("redtexture.png"));
        backgroundTexture = new Texture(Gdx.files.internal("graytexture.png"));
        manaTexture = new Texture(Gdx.files.internal("bluetexture.png"));
        font = new BitmapFont();
        font.getData().setScale(1.5f); // Make the text larger
        createPlayerHUD();
        
        // Initialize debug instance
        debug = new debug(spriteBatch, player, camera);

        // Create item bar
        createItemBar();
    }

    private void createPlayerHUD() {
        if (playerHUD != null) {
            // Clear existing HUD
            uiStage.clear();
        }
        if (player != null) {
            playerHUD = new PlayerHUD(uiStage, player, healthTexture, backgroundTexture, manaTexture, backgroundTexture);
        }
    }

    private void createItemBar() {
        if (player != null) {
            itemBar = new ItemBar(uiStage, player, 20, 20, 32, 0);
        }
    }

    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera position to follow player
        if (player != null && player.getBody() != null) {
            camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
            camera.zoom = 0.7f;
            camera.update();
        }

        // Apply game viewport and render game elements
        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        // Render map with layer-based rendering
        if (mapRenderer.getMap() != null) {
            mapRenderer.setView(camera);
            
            // Get all layers
            com.badlogic.gdx.maps.MapLayers layers = mapRenderer.getMap().getLayers();
            boolean playerLayerRendered = false;
            
            // Begin sprite batch for map rendering
            spriteBatch.begin();
            // Render each layer
            for (int i = 0; i < layers.getCount(); i++) {
                com.badlogic.gdx.maps.MapLayer layer = layers.get(i);
                
                // If we hit the "Player" layer, render the player and enemies
                if (layer.getName().equals("Player") && !playerLayerRendered) {
                    // End batch for map rendering
                    spriteBatch.end();
                    
                    // Render player, enemies, and boss
                    spriteBatch.begin();
                    for (Door door : mapManager.getDoors()) {
                        door.render(spriteBatch);
                    }
                    taskBoard = mapManager.getTaskBoard();
                    if (taskBoard != null) {
                        taskBoard.render(spriteBatch);
                    }

                    if(items != null) {
                        for (Item item : items) {
                            item.render(spriteBatch);
                        }
                    }
                    
                    if (player != null) {
                        player.render(spriteBatch);
                        if(showDebug) {
                            debug.playerDebug(player);
                        }
                    }
                    if(enemies != null) {
                        for (Enemy enemy : enemies) {
                            enemy.render(spriteBatch);
                            if(showDebug) {
                                debug.enemyDebug(enemy);
                            }
                        }
                    }
                    spriteBatch.end();
                    // Begin batch for next layer
                    spriteBatch.begin();
                    playerLayerRendered = true;
                }
                
                // Render the current layer if it's a tile layer
                if (layer instanceof com.badlogic.gdx.maps.tiled.TiledMapTileLayer) {
                    mapRenderer.renderTileLayer((com.badlogic.gdx.maps.tiled.TiledMapTileLayer) layer);
                }
            }
            // End batch for map rendering
            spriteBatch.end();
            
            // If no "Player" layer was found, render player and enemies at the end
            if (!playerLayerRendered) {
                spriteBatch.begin();
                if (player != null) {
                    player.render(spriteBatch);
                    if(showDebug) {
                        debug.playerDebug(player);
                    }
                }
                if(enemies != null) {
                    for (Enemy enemy : enemies) {
                        enemy.render(spriteBatch);
                        if(showDebug) {
                            debug.enemyDebug(enemy);
                        }
                    }
                }
                if(items != null) {
                    for (Item item : items) {
                        item.render(spriteBatch);
                    }
                }
                spriteBatch.end();
            }
        }

        // Render Box2D debug only when explicitly enabled
        if (showDebug) {
            box2DDebugRenderer.render(world, camera.combined);
        }

        // Render UI with fixed position
        uiStage.getViewport().apply();
        uiStage.act(delta);
        uiStage.draw();
        if (playerHUD != null) {
            playerHUD.update();
        }
        
        // Render boss health bar if boss exists
        if (enemies != null) {
            for (Enemy enemy : enemies) {
                if (enemy.getCharacterType() == CharacterType.BOSS) {
                    // Switch to screen coordinates for UI elements

                    spriteBatch.setProjectionMatrix(uiStage.getCamera().combined);
                    spriteBatch.begin();
                    
                    // Fixed size for boss health bar
                    float barWidth = Gdx.graphics.getWidth() * 0.4f;
                    float barHeight = Gdx.graphics.getHeight() * 0.05f;
                    float x = (Gdx.graphics.getWidth() - barWidth) / 2;
                    float y = Gdx.graphics.getHeight() - barHeight - 20;
                    
                    // Draw background (full width)
                    spriteBatch.draw(backgroundTexture, x, y, barWidth, barHeight);
                    
                    // Calculate health percentage and draw health bar
                    float healthPercent = enemy.getHealth() / (float)enemy.getMaxHealth();
                    spriteBatch.draw(healthTexture, x, y, barWidth * healthPercent, barHeight);
                    font.getData().setScale((float) (Gdx.graphics.getWidth())/(Gdx.graphics.getHeight())); // Set font size
                    // Draw health text
                    String healthText = enemy.getHealth() + " / " + enemy.getMaxHealth();
                    float textWidth = font.getXHeight() * healthText.length()*0.6f;
                    float textX = x + (barWidth - textWidth) / 2.5f;
                    float textY = y + 25;
                    font.draw(spriteBatch, healthText, textX, textY);
                    
                    spriteBatch.end();
                    
                    // Switch back to world coordinates
                    spriteBatch.setProjectionMatrix(camera.combined);
                    break; // Only render for first boss found
                }
            }
        }
        
        if(player != null) {
            player.renderDeathOverlay(spriteBatch);
        }

        // Update and draw item bar
        if (itemBar != null) {
            itemBar.update();
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

    public boolean isShowDebug() {
        return showDebug;
    }

    public void updatePlayer(Player player) {
        this.player = player;
        createPlayerHUD();
        createItemBar();
    }
    public void updateEnemy(Array<Enemy> enemies) {
        this.enemies = enemies;
    }
    public void updateItem(Array<Item> items) {
        this.items = items;
    }
    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        mapRenderer.dispose();
        uiStage.dispose();
        healthTexture.dispose();
        backgroundTexture.dispose();
        manaTexture.dispose();
        if (debug != null) {
            debug.dispose();
        }
        itemBar.dispose();
    }

    @Override
    public void mapChanged(Map map) {
        mapRenderer.setMap(map.getTiledMap());
    }
}
