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

/**
 * Main renderer class responsible for rendering all game elements including the map, entities, UI, and debug information.
 * Implements Disposable for proper resource cleanup and MapListener for map change events.
 */
public class GameRenderer implements Disposable, MapListener {

    public static final String TAG = GameRenderer.class.getSimpleName();
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch spriteBatch;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GLProfiler profiler;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final Stage uiStage;
    private PlayerHUD playerHUD;
    private Player player;
    private final Texture healthTexture;
    private final Texture backgroundTexture;
    private final Texture manaTexture;
    private final BitmapFont font;
    private Array<Enemy> enemies;
    private Array<Item> items;
    private ItemBar itemBar;
    private Array<Door> doors;
    private final MapManager mapManager;
    private final EntityRenderer entityRenderer;
    private TaskBoard taskBoard;

    /**
     * Creates a new GameRenderer instance and initializes all necessary components.
     * Sets up the viewport, camera, sprite batch, map renderer, and UI elements.
     * 
     * @param context The GamePanel context that provides access to game resources and state
     */
    public GameRenderer(final GamePanel context) {
        viewport = context.getViewport();
        camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();
        player = context.getPlayer();
        enemies = context.getEnemy();
        items = context.getItems();
        taskBoard = context.getTaskBoard();
        entityRenderer = new EntityRenderer(context);
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, spriteBatch);
        context.getMapManager().addListener(this);

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        box2DDebugRenderer = new Box2DDebugRenderer();
        this.mapManager = context.getMapManager();

        // UI setup with ScreenViewport for fixed UI elements
        uiStage = new Stage(new ScreenViewport(), spriteBatch);
        healthTexture = new Texture(Gdx.files.internal("Ui/redtexture.png"));
        backgroundTexture = new Texture(Gdx.files.internal("Ui/graytexture.png"));
        manaTexture = new Texture(Gdx.files.internal("Ui/bluetexture.png"));
        font = new BitmapFont();
        font.getData().setScale(1.5f); // Make the text larger
        createPlayerHUD();

        // Create item bar
        createItemBar();
    }

    /**
     * Renders all game elements including the map, entities, UI, and debug information.
     * Handles layer-based rendering and camera following the player.
     * 
     * @param delta The time elapsed since the last frame
     */
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
                    for (Door door : doors) {
                        entityRenderer.renderDoor(spriteBatch, door);
                    }
                    if (taskBoard != null) {
                        entityRenderer.renderTaskBoard(spriteBatch, taskBoard);
                    };
                    for (Enemy enemy : enemies) {
                        entityRenderer.renderAll(spriteBatch, enemy, delta);
                    }
                    if (items != null) {
                        for (Item item : items) {
                            entityRenderer.renderAll(spriteBatch, item, delta);
                        }
                    }
                    if (player != null) {
                        entityRenderer.renderAll(spriteBatch, player, delta);
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
                    entityRenderer.renderAll(spriteBatch, player, delta);
                }
                if(enemies != null) {
                    for (Enemy enemy : enemies) {
                        entityRenderer.renderAll(spriteBatch, enemy, delta);
                    }
                }
                if (items != null) {
                    for (Item item : items) {
                        entityRenderer.renderAll(spriteBatch, item, delta);
                    }
                }
                spriteBatch.end();
            }
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

    /**
     * Updates the viewport and UI stage size when the window is resized.
     * 
     * @param width The new width of the window
     * @param height The new height of the window
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height, true);
    }

    /**
     * Updates the player reference and recreates the player HUD.
     * 
     * @param player The new player instance
     */
    public void updatePlayer(Player player) {
        this.player = player;
        createPlayerHUD();
        createItemBar();
    }

    /**
     * Updates the list of enemies to be rendered.
     * 
     * @param enemies The new array of enemies
     */
    public void updateEnemy(Array<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Updates the list of items to be rendered.
     * 
     * @param items The new array of items
     */
    public void updateItem(Array<Item> items) {
        this.items = items;
    }

    /**
     * Updates the list of doors to be rendered from the current map.
     */
    public void updateDoors() {
        this.doors = mapManager.getDoors();
    }

    /**
     * Updates the task board reference from the current map.
     */
    public void updateTaskBoard() {
        this.taskBoard = mapManager.getTaskBoard();
    }

    /**
     * Disposes of all resources used by the renderer.
     * Should be called when the renderer is no longer needed to prevent memory leaks.
     */
    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        mapRenderer.dispose();
        uiStage.dispose();
        healthTexture.dispose();
        backgroundTexture.dispose();
        manaTexture.dispose();
        itemBar.dispose();
    }

    /**
     * Handles map change events by updating the map renderer.
     * 
     * @param map The new map to render
     */
    @Override
    public void mapChanged(Map map) {
        mapRenderer.setMap(map.getTiledMap());
    }

    /**
     * Creates or updates the player HUD with current player information.
     */
    private void createPlayerHUD() {
        if (playerHUD != null) {
            // Clear existing HUD
            uiStage.clear();
        }
        if (player != null) {
            playerHUD = new PlayerHUD(uiStage, player, healthTexture, backgroundTexture, manaTexture, backgroundTexture);
        }
    }

    /**
     * Creates or updates the item bar with current player inventory.
     */
    private void createItemBar() {
        if (player != null) {
            itemBar = new ItemBar(uiStage, player, 20, 20, 32, 0);
        }
    }
}
