package inf112.skeleton.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.KeyListener;
import inf112.skeleton.model.GamePanel;

/**
 * Abstract base class for game screens.
 * Provides common functionality for all screens, such as viewport management and access to shared resources.
 */
public class AbstractScreen  implements Screen, KeyListener {
    protected final GamePanel context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2DDebugRenderer;
    protected final KeyHandler keyHandler;
    protected final AudioHandler audioHandler;
    protected static final float ORIGINALWIDTH = 960; // Original window width
    protected static final float ORIGINALHEIGHT = 540;

     /**
     * Constructs an AbstractScreen instance.
     *
     * @param context The game context, providing access to shared resources.
     */
    public AbstractScreen(final GamePanel context) {
        this.context = context;
        this.viewport = context.getViewport();
        this.world = context.getWorld();
        this.box2DDebugRenderer = context.getBox2DDebugRenderer();
        this.keyHandler = context.getKeyHandler();
        this.audioHandler = context.getAudioHandler();
    }

    @Override
    public void show() {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.
    }

    @Override
    public void hide() {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.

    }

    @Override
    public void render(float delta) {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.
    }

    @Override
    public void resize(int width, int height) { 
        viewport.update(width, height);
    }

    @Override
    public void pause() {  
        // This method is intentionally left empty as it is meant to be overridden by subclasses.
    }

    @Override
    public void resume() {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.

    }

    @Override
    public void dispose() {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.

    }

    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.
    }

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
        // This method is intentionally left empty as it is meant to be overridden by subclasses.
    }
}
