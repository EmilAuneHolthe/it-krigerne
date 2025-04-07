package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.Enemy;
import inf112.skeleton.model.entity.EnemyFactory;
import inf112.skeleton.model.entity.Player;
import inf112.skeleton.model.entity.PlayerFactory;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.GameRenderer;

public class GameScreen extends AbstractScreen implements MapListener {
    private Player player;
    private Array<Enemy> enemies;
    private final World world;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final MapManager mapManager;
    private final GameRenderer gameRenderer;

    public GameScreen(GamePanel context) {
        super(context);
        assetManager = context.getAssetManager();
        this.camera = context.getCamera();
        this.world = context.getWorld();
        this.mapManager = context.getMapManager();
        this.gameRenderer = context.getGameRenderer();
        
        mapManager.addListener(this);
        mapManager.setMap(MapType.MAP_1);
        
        spawnEnemy();
        spawnPlayer();
    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);

        //Gdx.app.log("Debug", "FPS: " + Gdx.graphics.getFramesPerSecond());
   

        // Test map switching - should be moved to a proper input handler
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            mapManager.setMap(MapType.MAP_1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            mapManager.setMap(MapType.MAP_2);
        }
    }

    @Override
    public void show() {
        keyHandler.addListener(this);
        Gdx.input.setInputProcessor(keyHandler);
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        keyHandler.removeListener(this);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        player.playerInput(keyHandler, key);
        player.playerTakeDamage(keyHandler, key);
    }

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
        player.movePlayerReleased(keyHandler, key);
    }

    @Override
    public void mapChanged(Map map) {
        // Map change is handled by GameRenderer through MapListener
    }

    private void spawnPlayer() {
        if (context.getPlayer() == null) {
            context.setPlayer(PlayerFactory.createPlayer(context, world, mapManager.getCurrentMap()));
            System.out.println("Spawning player at" + context.getPlayer().getX() + " " + context.getPlayer().getY());
        }
        player = context.getPlayer();
        gameRenderer.updatePlayer(player);
    }

    private void spawnEnemy() {
        if (context.getEnemy() == null) {
            context.setEnemy(EnemyFactory.createEnemy(context, world, mapManager.getCurrentMap()));
            //System.out.println("Spawning enemy at" + context.getEnemy().getX() + " " + context.getEnemy().getY());
        }
        enemies = context.getEnemy();
        gameRenderer.updateEnemy(enemies);
    }
}



