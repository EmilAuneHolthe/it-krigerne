package inf112.skeleton.view;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
 * Main renderer class responsible for rendering all game elements.
 * Handles the rendering of the game map, entities (players, enemies, items), UI elements,
 * and debug information. Implements Disposable for resource cleanup and MapListener for map change events.
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
     * Renders all game elements in the correct order:
     * 1. Clears the screen
     * 2. Updates camera position
     * 3. Renders game elements (map, entities)
     * 4. Renders UI elements
     * 5. Renders boss health bar if present
     * 6. Renders player death overlay if needed
     * 7. Updates item bar
     * 8. Logs debug information
     * 
     * @param delta The time elapsed since the last frame
     */
    public void render(final float delta) {
        clearScreen();
        updateCamera();
        renderGameElements();
        renderUI(delta);
        renderBossHealthBar();
        renderPlayerDeathOverlay();
        updateItemBar();
        logDebugInfo();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    private void updateCamera() {
        if (player != null && player.getBody() != null) {
            camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
            camera.zoom = 0.7f;
            camera.update();
        }
    }

    private void renderGameElements() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        if (mapRenderer.getMap() != null) {
            mapRenderer.setView(camera);
            renderMapLayers();
        }
    }

    private void renderMapLayers() {
        com.badlogic.gdx.maps.MapLayers layers = mapRenderer.getMap().getLayers();
        boolean playerLayerRendered = false;

        spriteBatch.begin();
        for (int i = 0; i < layers.getCount(); i++) {
            com.badlogic.gdx.maps.MapLayer layer = layers.get(i);
            if (layer.getName().equals("Player") && !playerLayerRendered) {
                spriteBatch.end();
                renderEntities();
                spriteBatch.begin();
                playerLayerRendered = true;
            }
            if (layer instanceof TiledMapTileLayer tiledmaptilelayer) {
                mapRenderer.renderTileLayer(tiledmaptilelayer);
            }
        }
        spriteBatch.end();

        if (!playerLayerRendered) {
            renderEntities();
        }
    }

    private void renderEntities() {
        spriteBatch.begin();
        if (player != null) {
            entityRenderer.renderAll(spriteBatch, player);
        }
        if (enemies != null) {
            for (Enemy enemy : enemies) {
                entityRenderer.renderAll(spriteBatch, enemy);
            }
        }
        if (items != null) {
            for (Item item : items) {
                entityRenderer.renderAll(spriteBatch, item);
            }
        }
        if (doors != null) {
            for (Door door : doors) {
                entityRenderer.renderDoor(spriteBatch, door);
            }
        }
        if (taskBoard != null) {
            entityRenderer.renderTaskBoard(spriteBatch, taskBoard);
        }
        spriteBatch.end();
    }

    private void renderUI(float delta) {
        uiStage.getViewport().apply();
        uiStage.act(delta);
        uiStage.draw();
        if (playerHUD != null) {
            playerHUD.update();
        }
    }

    private void renderBossHealthBar() {
        if (enemies != null) {
            for (Enemy enemy : enemies) {
                if (enemy.getCharacterType() == CharacterType.BOSS) {
                    renderBossHealth(enemy);
                    break;
                }
            }
        }
    }

    private void renderBossHealth(Enemy enemy) {
        spriteBatch.setProjectionMatrix(uiStage.getCamera().combined);
        spriteBatch.begin();

        float barWidth = Gdx.graphics.getWidth() * 0.4f;
        float barHeight = Gdx.graphics.getHeight() * 0.05f;
        float x = (Gdx.graphics.getWidth() - barWidth) / 2;
        float y = Gdx.graphics.getHeight() - barHeight - 20;

        spriteBatch.draw(backgroundTexture, x, y, barWidth, barHeight);

        float healthPercent = enemy.getHealth() / (float) enemy.getMaxHealth();
        spriteBatch.draw(healthTexture, x, y, barWidth * healthPercent, barHeight);

        font.getData().setScale((float) (Gdx.graphics.getWidth()) / (Gdx.graphics.getHeight()));
        String healthText = enemy.getHealth() + " / " + enemy.getMaxHealth();
        float textWidth = font.getXHeight() * healthText.length() * 0.6f;
        float textX = x + (barWidth - textWidth) / 2.5f;
        float textY = y + 25;
        font.draw(spriteBatch, healthText, textX, textY);

        spriteBatch.end();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    private void renderPlayerDeathOverlay() {
        if (player != null) {
            player.renderDeathOverlay(spriteBatch);
        }
    }

    private void updateItemBar() {
        if (itemBar != null) {
            itemBar.update();
        }
    }

    private void logDebugInfo() {
        if (profiler.isEnabled()) {
            Gdx.app.debug(TAG, "Bindings: " + profiler.getTextureBindings());
            Gdx.app.debug(TAG, "Draw calls: " + profiler.getDrawCalls());
            profiler.reset();
        }
    }

    /**
     * Updates the viewport and UI stage size when the window is resized.
     * Ensures proper scaling and positioning of all UI elements.
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
     * Called when the player is created or respawned.
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
     * Called when enemies are spawned or removed.
     * 
     * @param enemies The new array of enemies
     */
    public void updateEnemy(Array<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Updates the list of items to be rendered.
     * Called when items are spawned or collected.
     * 
     * @param items The new array of items
     */
    public void updateItem(Array<Item> items) {
        this.items = items;
    }

    /**
     * Updates the list of doors to be rendered from the current map.
     * Called when changing maps or when doors are opened/closed.
     */
    public void updateDoors() {
        this.doors = mapManager.getDoors();
    }

    /**
     * Updates the task board reference from the current map.
     * Called when changing maps or when task board state changes.
     */
    public void updateTaskBoard() {
        this.taskBoard = mapManager.getTaskBoard();
    }

    /**
     * Disposes of all resources used by the renderer.
     * Should be called when the renderer is no longer needed to prevent memory leaks.
     * Cleans up textures, renderers, and UI elements.
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
     * Called when the player moves to a different map.
     * 
     * @param map The new map to render
     */
    @Override
    public void mapChanged(Map map) {
        mapRenderer.setMap(map.getTiledMap());
    }

    /**
     * Creates or updates the player HUD with current player information.
     * Called when the player is created or when the HUD needs to be refreshed.
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
     * Called when the player is created or when inventory changes.
     */
    private void createItemBar() {
        if (player != null) {
            itemBar = new ItemBar(uiStage, player, 20, 20, 32, 0);
        }
    }
}
