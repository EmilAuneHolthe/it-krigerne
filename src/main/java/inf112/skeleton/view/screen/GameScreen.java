package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.WorldFunctions;

import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.view.GameRenderer;

public class GameScreen extends AbstractScreen{

    private final OrthographicCamera camera;
    private final GameRenderer gameRenderer;
    private final PlayerInteractions playerInteractions;
    private final WorldFunctions  worldFunctions;


    public GameScreen(GamePanel context) {
        super(context);
        this.camera = context.getCamera();
        this.gameRenderer = context.getGameRenderer();
        playerInteractions = new PlayerInteractions(context, context.getPlayer());
        worldFunctions = new WorldFunctions(context);
  
        gameRenderer.updateDoors();
    }

    @Override   
    public void render(float delta) {
        gameRenderer.render(delta);
        worldFunctions.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        gameRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    @Override
    public void show() {
        keyHandler.addListener(this);

    }
    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        context.getPlayerController().playerInput(keyHandler, key);
    }    

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
        context.getPlayer().movePlayerReleased(keyHandler, key);
    }

}




