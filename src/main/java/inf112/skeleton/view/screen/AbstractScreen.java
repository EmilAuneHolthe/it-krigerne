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

public class AbstractScreen  implements Screen, KeyListener {
    protected final GamePanel context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2DDebugRenderer;
    protected final KeyHandler keyHandler;
    protected final AudioHandler audioHandler;
    protected final float originalWidth = 960; // Original window width
    protected final float originalHeight = 540;


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
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        
    }

    @Override
    public void resize(int width, int height) { 
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
    }


    @Override
    public void dispose() {

    }

    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
    }

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
    }
    
}
