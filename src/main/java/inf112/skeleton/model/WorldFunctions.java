package inf112.skeleton.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.entity.enemy.Enemy;
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

/**
 * Provides utility functions for managing the game world and its entities.
 * This class handles the creation and management of game entities, including players,
 * enemies, items, and their interactions within the game world.
 */
public class WorldFunctions{
    /** The game panel instance */
    private final GamePanel context;
    /** The Box2D physics world */
    private final World world;
    /** The map manager for handling game maps */
    private final MapManager mapManager;
    /** The map changer for transitioning between maps */
    private final MapChanger mapChanger;
    /** The game renderer for rendering game entities */
    private final GameRenderer gameRenderer;
    /** The audio handler for managing game sounds */
    private final AudioHandler audioHandler;
    /** The player instance */
    private Player player;
    /** The player interactions handler */
    private PlayerInteractions playerInteractions;
    /** Collection of enemies in the current map */
    private static Array<Enemy> enemies = new Array<>();
    /** Collection of items in the current map */
    private final Array<Item> items = new Array<>();
    /** Collection of border areas in the current map */
    private Array<Borders> borders = new Array<>();
    /** Flag indicating if the player has achieved victory */
    public static Boolean victory = false;
    /** Delta time accumulator */
    private float dTime = 0;

    /**
     * Creates a new WorldFunctions instance and initializes the game world.
     * Sets up the map, player, enemies, items, and task board.
     *
     * @param context The game panel context
     */
    public WorldFunctions(GamePanel context){
        this.mapManager = context.getMapManager();
        this.gameRenderer = context.getGameRenderer();
        this.context = context;
        this.world = context.getWorld();
        this.audioHandler = context.getAudioHandler();
        mapChanger = new MapChanger();
        mapManager.setMap(MapType.MAP_START);

        borders = mapManager.getCurrentMap().getBorders("Interact");
        playerInteractions = new PlayerInteractions(context, player);
        context.setPlayerInteractions(playerInteractions);
        spawnEnemy();
        spawnPlayer();
        spawnItem();
        spawnTaskBoard();
       

        enemies = context.getEnemy();


    }

    /**
     * Updates the game world state.
     * Handles player movement, mana regeneration, enemy updates, and interactions.
     *
     * @param delta Time elapsed since last update
     */
    public void update(float delta){
        if (victory) {
            setVictory();
        }
        player.getMovement().update();
        // Update player for mana regeneration
        if (player != null) {
            player.regenerateMana(delta);
        }

        dTime += 0.5F;
        if (dTime >= 25) {
            for(Enemy enemy : enemies) {
                if (player != null) {
                    enemy.update(player);
                }
            }
            dTime = 0;
        }
            borders = mapManager.getCurrentMap().getBorders("Interact");
            if(borders != null) {
            boolean isInsideTaskBoard = false;
            for(Borders border : borders) {
                String name;
                name = border.isInside(player.getX(), player.getY());
                if (name!= null) {
                    if(name.equals("TaskBoard")) {
                        isInsideTaskBoard = true;
                    }
                    else if(player.hasKey() && Boolean.TRUE.equals(mapManager.openDoor(name))){
                    player.removeKey();
                    context.getAudioHandler().playAudio(AudioTypes.DOOR);
                    }
                }
            }
            if(mapManager.getTaskBoard() != null) {
                mapManager.getTaskBoard().setActive(isInsideTaskBoard);
            }
            teleportPlayer();
    }
    }

    /**
     * Handles victory condition and transitions to victory screen.
     */
    public void setVictory() {
      if (victory) {
          context.setScreen(ScreenType.VICTORY);
      }     
  }

    /**
     * Spawns the player in the current map.
     * Creates a new player if one doesn't exist.
     */
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

    /**
     * Spawns items in the current map.
     * Creates items based on item spawn points in the map.
     */
    private void spawnItem() {
        ItemFactory factory = new ItemFactory(context, world);
        Array<Item> items = factory.createItemFromMap(mapManager.getCurrentMap());
        context.setItems(items);
        gameRenderer.updateItem(items);
    }

    /**
     * Sets the player instance.
     *
     * @param player The player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Changes the current map and handles the transition.
     * Removes old objects, spawns new ones, and updates the game state.
     *
     * @param mapType The type of map to change to
     */
    public void changeMap(MapType mapType) {
        mapManager.setMap(mapType);
        mapChanger.removeObjects(world, mapManager.getCurrentMap(), enemies);
        enemies.clear();
        player.setKey(false);
        context.setEnemy(enemies);
        mapChanger.movePlayer(world, mapManager.getCurrentMap(), player);
        spawnEnemy();
        enemies = context.getEnemy();
        spawnItem();
        gameRenderer.updateDoors();
        }


    /**
     * Handles player teleportation between maps.
     * Checks player position and triggers map changes when appropriate.
     */
    private void teleportPlayer() {
        float playerX = player.getBody().getPosition().x ;
        float playerY = player.getBody().getPosition().y ;

        if ((playerX >= 72.4 && playerX <= 72.6 && playerY >= 75.3 && playerY <= 75.5) && (mapManager.getCurrentMapType() == MapType.MAP_START)) {
            changeMap(MapType.MAP_CASTLE);
        }
        if ((playerX >= 83.4 && playerX <= 83.6 && playerY >= 77.9 && playerY <= 78.1) && (mapManager.getCurrentMapType() == MapType.MAP_CASTLE)) {
            audioHandler.stopMusic();
            audioHandler.playAudio(AudioTypes.BOSSMUSIC);
            changeMap(MapType.MAP_BOSS);
        }
    }

    /**
     * Spawns the task board in the current map.
     * Updates the game renderer with the new task board.
     */
    public void spawnTaskBoard(){
        mapManager.spawnTaskBoard();
        gameRenderer.updateTaskBoard();
    }

    /**
     * Spawns enemies in the current map.
     * Creates enemies based on enemy spawn points in the map.
     */
    private void spawnEnemy() {
        EnemyFactory factory = new EnemyFactory(context, world);
        Array<Enemy> enemies = factory.createEnemiesFromMap(mapManager.getCurrentMap());
        context.setEnemy(enemies);
        gameRenderer.updateEnemy(enemies);
    }

    /**
     * Gets all enemies in the current map.
     *
     * @return Array of enemies
     */
    public static Array<Enemy> getEnemies () {
        return enemies;
    }
}