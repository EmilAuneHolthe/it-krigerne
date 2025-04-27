package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import java.security.Key;
import java.util.ArrayList;

import javax.swing.border.Border;

import inf112.skeleton.model.entity.door.Door;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyController;
import inf112.skeleton.model.entity.enemy.EnemyFactory;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemFactory;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerFactory;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.model.map.Borders;
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
    private Array<Item> items;
    private final OrthographicCamera camera;
    private final MapManager mapManager;
    private final GameRenderer gameRenderer;
    private final PlayerInteractions playerInteractions;
    private final EnemyController enemyController;
    private final MapChanger mapChanger;
    private final ArrayList<Borders> borders;
    public GameScreen(GamePanel context) {
        super(context);
        this.camera = context.getCamera();
        this.mapManager = context.getMapManager();
        this.gameRenderer = context.getGameRenderer();

        mapManager.addListener(this);
        mapManager.setMap(MapType.MAP_1);
        
        spawnEnemy();
        spawnPlayer();
        spawnItem();
        playerInteractions = new PlayerInteractions(context);
        enemies = context.getEnemy();
        enemyController = new EnemyController(context, world, enemies, player);
        context.setPlayerInteractions(playerInteractions);
        mapChanger = new MapChanger();
        borders = mapManager.getCurrentMap().getBorders("Interact");
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
        if(player.hasKey()){
            for(Borders border : borders) {
                String name;
                name = border.isInside(player.getX(), player.getY());
                if (name!= null) {
                    if(mapManager.openDoor(name)){
                    player.removeKey();
                    }
                }
            }
            float playerX = player.getBody().getPosition().x ;
            float playerY = player.getBody().getPosition().y ;
            if (playerX >= 72.4 && playerX <= 72.6 && playerY >= 75.3 && playerY <= 75.5){
            changeMap();
            }
   
    }
        //Gdx.app.log("Debug", "FPS: " + Gdx.graphics.getFramesPerSecond());

        // Test map switching - should be moved to a proper input handler
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            playerInteractions.attackEnemy(player, enemies);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            changeSecondMap();
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
    private void spawnItem() {
        ItemFactory factory = new ItemFactory(context, world);
        Array<Item> items = factory.createItemFromMap(mapManager.getCurrentMap());
        context.setItems(items);
        gameRenderer.updateItem(items);
    }
    public void changeMap() {
        mapManager.setMap(MapType.MAP_2);
        mapChanger.removeObjects(world, mapManager.getCurrentMap(), enemies);
        enemies.clear();
        player.setKey(false);
        context.setEnemy(enemies);
        mapChanger.movePlayer(world, mapManager.getCurrentMap(), player);
        spawnEnemy();
        enemies = context.getEnemy();
        enemyController.updateEnemies(enemies);
        spawnItem();
        }

    public void changeSecondMap() {
        mapManager.setMap(MapType.MAP_3);
        mapChanger.removeObjects(world, mapManager.getCurrentMap(), enemies);
        enemies.clear();
        player.setKey(false);
        context.setEnemy(enemies);
        mapChanger.movePlayer(world, mapManager.getCurrentMap(), player);
        spawnEnemy();
        enemies = context.getEnemy();
        enemyController.updateEnemies(enemies);
        spawnItem();
    }
}




