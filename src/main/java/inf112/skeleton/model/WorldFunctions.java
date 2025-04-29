package inf112.skeleton.model;

import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import static inf112.skeleton.model.GamePanel.UNIT_SCALE;
import inf112.skeleton.audio.AudioTypes;
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
import inf112.skeleton.model.map.MapChanger;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.view.screen.ScreenType;

public class WorldFunctions {
    private World world;
    private Player player;
    public static Boolean victory = false;
    private final GamePanel context;
    private float dTime = 0;
    private Array<Borders> borders;
    private MapManager mapManager;
    private GameRenderer gameRenderer;
    private MapChanger mapChanger;
    private EnemyController enemyController;
    private static Array<Enemy> enemies = new Array<>();
    private Array<Item> items = new Array<>();
    private PlayerInteractions playerInteractions;
  public WorldFunctions(GamePanel context){
    System.out.println("WorldFunctions");
    this.mapManager = context.getMapManager();
    this.gameRenderer = context.getGameRenderer();
    this.context = context;
    this.world = context.getWorld();
    mapChanger = new MapChanger();

    mapManager.setMap(MapType.MAP_START);

    borders = mapManager.getCurrentMap().getBorders("Interact");
    enemyController = new EnemyController(enemies, player);
    playerInteractions = new PlayerInteractions(context);
    context.setPlayerInteractions(playerInteractions);
    spawnEnemy();
    spawnPlayer();
    spawnItem();
    spawnTaskBoard();

  }
  public void update(float delta){
        if (victory) {
            setVictory();
        }
        
        // Update player for mana regeneration
        if (player != null) {
            player.regenerateMana(delta);
        }

        dTime += 0.5;
        if (dTime >= 25) {
            enemyController.sight();
            dTime = 0;
        }
            borders = mapManager.getCurrentMap().getBorders("Interact");
            if(borders != null) {
            Boolean isInsideTaskBoard = false;
            for(Borders border : borders) {
                String name;
                name = border.isInside(player.getX(), player.getY());
                if (name!= null) {
                    if(name.equals("TaskBoard")) {
                        isInsideTaskBoard = true;
                    }
                    else if(player.hasKey() && Boolean.TRUE.equals(mapManager.openDoor(name))){
                        System.out.println(name);
                    player.removeKey();
                    context.getAudioHandler().playAudio(AudioTypes.DOOR);
                    }
                }
            }
            if(mapManager.getTaskBoard() != null) {
                mapManager.getTaskBoard().setActive(isInsideTaskBoard);
            }
            isInsideTaskBoard = false;
            teleportPlayer();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameRenderer.setShowDebug(!gameRenderer.isShowDebug());
        }
    }
    }
    public void setVictory() {
      if (victory) {
          context.setScreen(ScreenType.VICTORY);
      }     
  }
      private void spawnPlayer() {
        if (context.getPlayer() == null) {
            PlayerFactory factory = new PlayerFactory(context, world);
            context.setPlayer(factory.createPlayer(
                mapManager.getCurrentMap().getPlayerSpawn().x * UNIT_SCALE,
                mapManager.getCurrentMap().getPlayerSpawn().y * UNIT_SCALE,
                CharacterType.SOLDIER
            ));
        }
        player = context.getPlayer();
        gameRenderer.updatePlayer(player);
    }
        private void spawnItem() {
        ItemFactory factory = new ItemFactory(context, world);
        Array<Item> items = factory.createItemFromMap(mapManager.getCurrentMap());
        context.setItems(items);
        gameRenderer.updateItem(items);
    }
        public void setPlayer(Player player) {
        this.player = player;
    }
    public void changeMap(MapType mapType) {
        mapManager.setMap(mapType);
        mapChanger.removeObjects(world, mapManager.getCurrentMap(), enemies);
        enemies.clear();
        player.setKey(false);
        context.setEnemy(enemies);
        mapChanger.movePlayer(world, mapManager.getCurrentMap(), player);
        spawnEnemy();
        enemies = context.getEnemy();
        enemyController.updateEnemies(enemies);
        spawnItem();
        gameRenderer.updateDoors();
        }


    private void teleportPlayer() {
        float playerX = player.getBody().getPosition().x ;
        float playerY = player.getBody().getPosition().y ;

        if ((playerX >= 72.4 && playerX <= 72.6 && playerY >= 75.3 && playerY <= 75.5) && (mapManager.getCurrentMapType() == MapType.MAP_START)) {
            changeMap(MapType.MAP_CASTLE);
        }
        if ((playerX >= 83.4 && playerX <= 83.6 && playerY >= 77.9 && playerY <= 78.1) && (mapManager.getCurrentMapType() == MapType.MAP_CASTLE)) {
            changeMap(MapType.MAP_BOSS);
        }
    }
    public void spawnTaskBoard(){
        mapManager.spawnTaskBoard();
        gameRenderer.updateTaskBoard();
    }
        private void spawnEnemy() {
        EnemyFactory factory = new EnemyFactory(context, world);
        Array<Enemy> enemies = factory.createEnemiesFromMap(mapManager.getCurrentMap());
        context.setEnemy(enemies);
        gameRenderer.updateEnemy(enemies);
    }

    public static Array<Enemy> getEnemies () {
        return enemies;
    }
  }