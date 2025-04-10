package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import static inf112.skeleton.model.GamePanel.UNIT_SCALE;
import inf112.skeleton.model.entity.Enemy;
import inf112.skeleton.model.entity.EnemyController;
import inf112.skeleton.model.entity.EnemyFactory;
import inf112.skeleton.model.entity.Player;
import inf112.skeleton.model.entity.PlayerFactory;
import inf112.skeleton.model.entity.PlayerInteractions;
import inf112.skeleton.model.entity.CharacterType;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.model.map.MapChanger;

public class GameScreen extends AbstractScreen implements MapListener {
    private float dTime = 0;
    private Player player;
    private Array<Enemy> enemies;
    private final OrthographicCamera camera;
    private final MapManager mapManager;
    private final GameRenderer gameRenderer;
    private final PlayerInteractions playerInteractions;
    private final EnemyController enemyController;
    private final MapChanger mapChanger;
    public GameScreen(GamePanel context) {
        super(context);
        this.camera = context.getCamera();
        this.mapManager = context.getMapManager();
        this.gameRenderer = context.getGameRenderer();

        mapManager.addListener(this);
        mapManager.setMap(MapType.MAP_1);
        
        spawnEnemy();
        spawnPlayer();
        playerInteractions = new PlayerInteractions(context);
        enemies = context.getEnemy();
        enemyController = new EnemyController(context, world, enemies, player);
        context.setPlayerInteractions(playerInteractions);
        mapChanger = new MapChanger();
    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);

        // Update player for mana regeneration
        if (player != null) {
            player.regenerateMana(delta);
        }

        dTime += 0.5;
        if (dTime >= 25) {
            enemyController.sight();
            dTime = 0;
        }
        //Gdx.app.log("Debug", "FPS: " + Gdx.graphics.getFramesPerSecond());

        // Test map switching - should be moved to a proper input handler
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            playerInteractions.attackEnemy(player, enemies);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            mapManager.setMap(MapType.MAP_2);
            mapChanger.removeObjects(world, mapManager.getCurrentMap(), enemies);
            enemies.clear();
            context.setEnemy(enemies);
            mapChanger.movePlayer(world, mapManager.getCurrentMap(), player);
            spawnEnemy();
            enemies = context.getEnemy();
            enemyController.updateEnemies(enemies);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameRenderer.setShowDebug(!gameRenderer.isShowDebug());
        }
    }

    @Override
    public void show() {
        keyHandler.addListener(this);
        Gdx.input.setInputProcessor(keyHandler);
        
        // Always spawn a new player when showing the game screen
        spawnPlayer();
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
            PlayerFactory factory = new PlayerFactory(context, world, keyHandler);
            context.setPlayer(factory.createPlayer(
                mapManager.getCurrentMap().getPlayerSpawn().x * UNIT_SCALE,
                mapManager.getCurrentMap().getPlayerSpawn().y * UNIT_SCALE,
                CharacterType.SOLDIER
            ));
        }
        player = context.getPlayer();
        gameRenderer.updatePlayer(player);
    }

    private void spawnEnemy() {
        EnemyFactory factory = new EnemyFactory(context, world);
        Array<Enemy> enemies = factory.createEnemiesFromMap(mapManager.getCurrentMap());
        context.setEnemy(enemies);
        gameRenderer.updateEnemy(enemies);
    }
}




