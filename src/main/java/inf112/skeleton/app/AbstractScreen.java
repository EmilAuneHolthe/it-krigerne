package inf112.skeleton.app;

import javax.swing.Box;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.skeleton.app.GamePanel;

public class AbstractScreen  implements Screen{
    protected final GamePanel context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2DDebugRenderer;

    public AbstractScreen(final GamePanel context) {
        this.context = context;
        this.viewport = context.getViewport();
        this.world = context.getWorld();
        this.box2DDebugRenderer = context.getBox2DDebugRenderer();
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

    }

    @Override
    public void dispose() {

    }
    
    
}


