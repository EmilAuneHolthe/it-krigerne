package inf112.skeleton.model;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.security.Key;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.view.screen.*; 

public class GamePanel extends Game {
    private static final String TAG = GamePanel.class.getSimpleName();

    // Graphics
    private SpriteBatch spriteBatch;
    private static GamePanel instance;
    private OrthographicCamera camera;

    // Screen management
    private EnumMap<ScreenType, AbstractScreen> screenCache;
    private FitViewport screenViewport;
    private World world;
    private WorldContactListener worldContactListener;
    private Box2DDebugRenderer box2DDebugRenderer;


    // Pixel to meter ratio
    public static final short BIT_Player = 1<<0;
    public static final float UNIT_SCALE = 1/32f; // 1 meter = 32 pixels
    public static final short BIT_Box = 1<<1;
    public static final short BIT_Ground = 1<<2;
    private static final float FIXED_TIME_STEP = 1/60f;
    private float accumulator;

    private AssetManager assetManager;
    private KeyHandler keyHandler;
    private AudioHandler audioHandler;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        accumulator = 0;
        spriteBatch = new SpriteBatch();        
        
        // Box2D
        Box2D.init(); // Initialize Box2D
        world = new World(new Vector2(0, 0), true); // Create a new world with gravity
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);
        box2DDebugRenderer = new Box2DDebugRenderer(); // Create a new debug renderer

        //init assetManager
        assetManager = new AssetManager();  
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));
        camera = new OrthographicCamera();
        screenViewport = new FitViewport(25 * 32 * UNIT_SCALE, 14 * 32 * UNIT_SCALE, camera);
        screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);

        //Audio
        audioHandler = new AudioHandler(this);
        for(final AudioTypes audioType : AudioTypes.values()) { // Load all audio files
            assetManager.load(audioType.getPath(), (Class<?>) (audioType.isMusic() ? Music.class : Sound.class));
        }
        assetManager.finishLoading();  // Ensures assets are loaded before use

        //Input 
        keyHandler = new KeyHandler();  // Initialize KeyHandler

        setScreen(ScreenType.MAIN_MENU);
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


    public void setScreen(final ScreenType screenType) {
        final Screen screen = screenCache.get(screenType);
        if (screen == null) {
            // Skjerm finnes ikke fra før
            try{
                Gdx.app.debug(TAG, "Lager ny skjerm" + screenType);
                final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(),GamePanel.class).newInstance(this);
                screenCache.put(screenType, newScreen);
                setScreen(newScreen);
                } catch (ReflectionException e) {
                    throw new GdxRuntimeException("Screen" + screenType + " kunne ikke lages", e);
                }
        }else {
            // Skjerm finnes fra før
            Gdx.app.debug(TAG, "Skjerm finnes fra før" + screenType);
            setScreen(screen);
    }
}

    @Override
    public void render() {
        super.render();

        //Gdx.app.debug(TAG, "" + Gdx.graphics.getDeltaTime());
        accumulator += Math.min(0.25f, Gdx.graphics.getDeltaTime());
        while (accumulator >= FIXED_TIME_STEP) {
            world.step(FIXED_TIME_STEP, 6, 2);
            accumulator -= FIXED_TIME_STEP;
        }  

        // final float alpha = accumulator / FIXED_TIME_STEP; DO NOT USE yet
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

    
}
