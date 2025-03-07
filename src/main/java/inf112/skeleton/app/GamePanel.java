package inf112.skeleton.app;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import inf112.skeleton.app.screen.*; 

public class GamePanel extends Game {
    private static final String TAG = GamePanel.class.getSimpleName();

    private static GamePanel instance;
    
    
    private EnumMap<ScreenType, AbstractScreen> screenCache;
    private FitViewport screenViewport;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public static final short BIT_Circle = 1<<0;
    public static final short BIT_Box = 1<<1;
    public static final short BIT_Ground = 1<<2;

    private static final float FIXED_TIME_STEP = 1/60f;
    private float accumulator;

    private AssetManager assetManager;
    

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        accumulator = 0;

        Box2D.init(); // Initialize Box2D
        world = new World(new Vector2(0, 9.81f), true); // Create a new world with gravity
        world.setContactListener(null);
        box2DDebugRenderer = new Box2DDebugRenderer(); // Create a new debug renderer

        screenViewport = new FitViewport(9, 16);
        screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
        setScreen(ScreenType.GAME);

        //init assetManager
        assetManager = new AssetManager();  
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));
    }

    public FitViewport getViewport() {return screenViewport;}

    public World getWorld() {return world;}

    public Box2DDebugRenderer getBox2DDebugRenderer() {return box2DDebugRenderer;}

    
    

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

        Gdx.app.debug(TAG, "" + Gdx.graphics.getDeltaTime());
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
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    

    
}