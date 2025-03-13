package inf112.skeleton.app.screen;

import javax.swing.Box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.skeleton.app.GamePanel;
import inf112.skeleton.controller.GameKeys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.KeyListener;

public class AbstractScreen  implements Screen, KeyListener {
    protected final GamePanel context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2DDebugRenderer;
    protected final KeyHandler keyHandler;


    public AbstractScreen(final GamePanel context) {
        this.context = context;
        this.viewport = context.getViewport();
        this.world = context.getWorld();
        this.box2DDebugRenderer = context.getBox2DDebugRenderer();
        keyHandler = context.getKeyHandler();
    }

    @Override
    public void show() {

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
    public void hide() {
        keyHandler.removeListener(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void keyPressed(KeyHandler keyHandler, GameKeys key) {
        System.err.println("blblbblbl" + key);
    }

    @Override
    public void keyReleased(KeyHandler keyHandler, GameKeys key) {
        // TODO Auto-generated methodwdw stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
