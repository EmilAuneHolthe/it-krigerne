package inf112.skeleton.model;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.EnumMap;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.PlayerController;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyFactory;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemFactory;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;
import inf112.skeleton.model.map.MapChanger;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.view.screen.*;
import inf112.skeleton.view.ui.PlayerHUD; 

public class GamePanel extends Game {
    private static final String TAG = GamePanel.class.getSimpleName();

    // Graphics
    private SpriteBatch spriteBatch;
    private static GamePanel instance;
    private OrthographicCamera camera;
    private FitViewport screenViewport;

    // Screen management
    private EnumMap<ScreenType, AbstractScreen> screenCache;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    // Pixel to meter ratio
    public static final short BIT_PLAYER = 1;
    public static final float UNIT_SCALE = 1/32f; // 1 meter = 32 pixels
    public static final short BIT_BOX = 1<<1;
    public static final short BIT_GROUND = 1<<2;
    private static final float FIXED_TIME_STEP = 1/60f;
    private float accumulator;

    public static final BodyDef BODY_DEF = new BodyDef();
    public static final FixtureDef FIXTURE_DEF = new FixtureDef();

    public static AssetManager assetManager;
    private KeyHandler keyHandler;
    private AudioHandler audioHandler;
    private MapManager mapManager;
    private GameRenderer gameRenderer;
    private Player player;
    private Array<Enemy> enemies;
    private PlayerInteractions playerInteractions;
    private PlayerController playerController;
    private Array<Item> items;
    private MapChanger mapChanger;
    private PlayerHUD playerHUD;
    
    private WorldFunctions worldFunctions;

        @Override
        public void create() {
            Gdx.app.setLogLevel(Application.LOG_INFO);
            accumulator = 0;
            spriteBatch = new SpriteBatch();        
            
            // Box2D
            Box2D.init(); // Initialize Box2D
            world = new World(new Vector2(0, 0), true); // Create a new world with gravity
            box2DDebugRenderer = new Box2DDebugRenderer(); // Create a new debug renderer
            
            //init assetManager
            assetManager = new AssetManager();  
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));
            camera = new OrthographicCamera();
            screenViewport = new FitViewport(25 * 32 * UNIT_SCALE, 14 * 32 * UNIT_SCALE, camera);
            screenCache = new EnumMap<>(ScreenType.class);
    
            //Audio
            audioHandler = new AudioHandler(this);
            for(final AudioTypes audioType : AudioTypes.values()) { // Load all audio files
                assetManager.load(audioType.getPath(), (Class<?>) (audioType.isMusic() ? Music.class : Sound.class));
            }
            assetManager.finishLoading();  // Ensures assets are loaded before use
    
            //Input 
            keyHandler = new KeyHandler();  // Initialize KeyHandler
    
            //MapManager
            mapManager = new MapManager(this);
    
            //GameRenderer
            gameRenderer = new GameRenderer(this);
            gameRenderer.setShowDebug(false);
    
            // Initialize MapChanger and EnemyController
            mapChanger = new MapChanger();
            enemies = new Array<>();
    
            setScreen(ScreenType.LOADING);
        }

        public void setWorldFunctions(WorldFunctions worldFunctions) {
            this.worldFunctions = worldFunctions;
    }

    //get-methods
    public FitViewport getViewport() {return screenViewport;}
    public World getWorld() {return world;}
    public Box2DDebugRenderer getBox2DDebugRenderer() {return box2DDebugRenderer;}
    public KeyHandler getKeyHandler() {return keyHandler;}
    public AssetManager getAssetManager() {return assetManager;}
    public SpriteBatch getSpriteBatch() {return spriteBatch;}
    public OrthographicCamera getCamera() {return camera;}
    public AudioHandler getAudioHandler() {return audioHandler;}
    public MapManager getMapManager() {return mapManager;}
    public GameRenderer getGameRenderer() {return gameRenderer;}
    public void setPlayer(Player player) {
        this.player = player;
        playerController = new PlayerController(player, playerInteractions,this);
    }
    
    public Player getPlayer() {
        return player;
    }
    public void setEnemy(Array<Enemy> enemies) {
        this.enemies = enemies;
    }
    public Array<Enemy> getEnemy() {
        return enemies;
    }
    public void setItems(Array<Item> items) {
        this.items = items;
    }
    public Array<Item> getItems() {
        return items;
    }
    /**
     * Resets the player by setting it to null.
     * This will cause a new player to be created when the game screen is shown again.
     */
    public void resetPlayer() {
        if (player != null) {
            // Hide the death overlay if it's visible
            if (player.getDeathOverlay() != null) {
                player.getDeathOverlay().hide();
            }
            player.dispose();
            player = null;
        }
    }

    public void setScreen(final ScreenType screenType) {
        final Screen screen = screenCache.get(screenType);
        if (screen == null) {
            //screen doesnt already exist
            try{
                Gdx.app.debug(TAG, "Making new screen" + screenType);
                final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(),GamePanel.class).newInstance(this);
                screenCache.put(screenType, newScreen);
                setScreen(newScreen);
                } catch (ReflectionException e) {
                    throw new GdxRuntimeException("Screen" + screenType + " couldn't be made", e);
                }
        }else {
            // screen already exists
            Gdx.app.debug(TAG, "screen already exists " + screenType);
            setScreen(screen);
    }
}

    @Override
    public void render() {
        super.render();
        
        accumulator += Math.min(0.25f, Gdx.graphics.getDeltaTime());
        while (accumulator >= FIXED_TIME_STEP) {
            world.step(FIXED_TIME_STEP, 6, 2);
            accumulator -= FIXED_TIME_STEP;
        }  

        // Only render game when in game screen
        if (getScreen() instanceof GameScreen) {
            gameRenderer.render(accumulator / FIXED_TIME_STEP);
        }

    }

    public void removeScreen(ScreenType type) {
        Screen screen = screenCache.remove(type);
        if (screen != null) {
            screen.dispose(); // Clean up resources
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        // Properly dispose of the world
        world.dispose();
        // Properly dispose of the debug renderer
        box2DDebugRenderer.dispose();
        // Properly dispose assetManager
        assetManager.dispose();
        spriteBatch.dispose();
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    public static void resetBodyAndFixtureDefinition(){
        BODY_DEF.position.set(0, 0);
        BODY_DEF.gravityScale = 0;
        BODY_DEF.type = BodyDef.BodyType.StaticBody;

        FIXTURE_DEF.isSensor = false;
        FIXTURE_DEF.restitution = 0.75f;
        FIXTURE_DEF.friction = 0.2f;
        FIXTURE_DEF.filter.categoryBits = BIT_GROUND;
        FIXTURE_DEF.filter.maskBits = -1;

    }

    public void setPlayerInteractions(PlayerInteractions playerInteractions) {
        this.playerInteractions = playerInteractions;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void changemap(MapType newMapType) {
        if (newMapType == null) {
            Gdx.app.error(TAG, "Cannot change map: newMapType is null");
            return;
        }

        if (mapManager == null) {
            Gdx.app.error(TAG, "Cannot change map: mapManager is null");
            return;
        }

        if (world == null) {
            Gdx.app.error(TAG, "Cannot change map: world is null");
            return;
        }

        if (player == null) {
            Gdx.app.error(TAG, "Cannot change map: player is null");
            return;
        }

        // Change the map
        mapManager.setMap(newMapType);

        // Remove existing objects
        if (enemies != null) {
            mapChanger.removeObjects(world, mapManager.getCurrentMap(), enemies);
            enemies.clear();
            setEnemy(enemies);
        }

        // Move player to new spawn point
        mapChanger.movePlayer(world, mapManager.getCurrentMap(), player);

        // Spawn new enemies
        spawnEnemy();
        enemies = getEnemy();
        // Spawn new items
        spawnItem();
        items = getItems();
    }

    private void spawnEnemy() {
        if (mapManager == null || mapManager.getCurrentMap() == null) {
            Gdx.app.error(TAG, "Cannot spawn enemies: mapManager or current map is null");
            return;
        }

        EnemyFactory factory = new EnemyFactory(this, world);
        Array<Enemy> newEnemies = factory.createEnemiesFromMap(mapManager.getCurrentMap());
        setEnemy(newEnemies);
    }

    private void spawnItem() {
        if (mapManager == null || mapManager.getCurrentMap() == null) {
            Gdx.app.error(TAG, "Cannot spawn items: mapManager or current map is null");
            return;
        }

        ItemFactory factory = new ItemFactory(this, world);
        Array<Item> newItems = factory.createItemFromMap(mapManager.getCurrentMap());
        setItems(newItems);
    }

    public void setPlayerHUD(PlayerHUD playerHUD) {
        this.playerHUD = playerHUD;
    }
    
    public void updateEquippedSwordHUD(String texturePath) {
        if (playerHUD != null) {
            playerHUD.updateEquippedSword(texturePath);
        }
    }
    public TaskBoard getTaskBoard() {
        return mapManager.getTaskBoard();
    }
}
