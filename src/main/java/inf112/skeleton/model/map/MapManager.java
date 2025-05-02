package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import java.util.EnumMap;
import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.door.Door;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    private final World world;
    private final Array<Body> bodies;
    private final Array<Door> doors;
    private TaskBoard taskBoard;
    private final AssetManager assetManager;
    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache;
    private final Array<MapListener> listners;

    /**
     * Creates a new instance of MapManager with access to world and assetManager via GamePanel.
     *
     * @param context The game's GamePanel that provides access to world and assets.
     */
    public MapManager(GamePanel context) {
        currentMapType = null;
        currentMap = null;
        world = context.getWorld();
        assetManager = context.getAssetManager();
        bodies = new Array<>();
        doors = new Array<>();
        mapCache = new EnumMap<>(MapType.class);
        listners = new Array<>();
        taskBoard = null;
    }

    /**
     * Adds a listener that will be notified whenever the map changes.
     *
     * @param listener The listener to add.
     */
    public void addListener(final MapListener listener) {
        listners.add(listener);
    }

    /**
     * Sets the game to a new map (MapType).
     * If the map is already loaded, it will be retrieved from cache.
     * Existing collision objects are removed and new ones are created.
     *
     * @param type The map type to load.
     */
    public void setMap(final MapType type) {
        if (currentMapType == type) {
            return;
        }
        if (currentMap != null) {
            world.getBodies(bodies);
            destroyCollisionAreas();
        }

        Gdx.app.debug(TAG, "Loading map: " + type);
        currentMap = mapCache.get(type);
        if (currentMap == null) {
            Gdx.app.debug(TAG, "Creatign new map: " + type);
            final TiledMap tiledMap = assetManager.get(type.getFilePath(), TiledMap.class);
            currentMap = new Map(tiledMap);
            mapCache.put(type, currentMap);
        }

        currentMapType = type;
        spawnCollisionsAreas();
        spawnDoors();
        spawnTaskBoard();

        for (final MapListener listener : listners) {
            listener.mapChanged(currentMap);
        }
    }

    /**
     * Removes all collision areas that have been created in the world.
     */
    private void destroyCollisionAreas() {
        Array<Body> toRemove = new Array<>();
        for (final Body body : bodies) {
            if ("GROUND".equals(body.getUserData())) {
                world.destroyBody(body);
                toRemove.add(body);
            }
        }
        bodies.removeAll(toRemove, true);
        Gdx.app.debug(TAG, "Destroyed " + toRemove.size + " GROUND bodies");
    }

    /**
     * Creates collision objects in the world based on CollisionAreas in the current map.
     * Ensures that duplicate/nearby points don't cause Box2D crashes.
     */
    private void spawnCollisionsAreas() {
        GamePanel.resetBodyAndFixtureDefinition();
        for (final CollisionArea collisionArea : currentMap.getColissionAreas()) {
            GamePanel.BODY_DEF.position.set(collisionArea.x(), collisionArea.y());
            GamePanel.BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(GamePanel.BODY_DEF);
            body.setUserData("GROUND");
            GamePanel.FIXTURE_DEF.filter.categoryBits = GamePanel.BIT_GROUND;
            GamePanel.FIXTURE_DEF.filter.maskBits = -1;

            float[] original = collisionArea.vertices();
            Array<Vector2> filtered = new Array<>();
            float threshold = 0.005f * 0.005f;

            for (int i = 0; i < original.length - 1; i += 2) {
                float x = original[i];
                float y = original[i + 1];
                Vector2 current = new Vector2(x, y);
                if (filtered.size == 0 || current.dst2(filtered.peek()) > threshold) {
                    filtered.add(current);
                }
            }

            if (filtered.size < 2) {
                Gdx.app.debug(TAG, "[WARNING] Ignored collision area: too few unique points.");
                continue;
            }

            final ChainShape cShape = new ChainShape();
            cShape.createChain(filtered.toArray(Vector2.class));
            GamePanel.FIXTURE_DEF.shape = cShape;
            body.createFixture(GamePanel.FIXTURE_DEF);
            cShape.dispose();
            bodies.add(body);
        }
    }

    /**
     * Creates door objects in the world based on door areas in the current map.
     * Each door is created with a physics body and added to the doors collection.
     */
    private void spawnDoors() {
        if(currentMap.getDoorsAreas().isEmpty()) {
            return;
        }
        GamePanel.resetBodyAndFixtureDefinition();
        doors.clear();
    
        for (Borders door : currentMap.getDoorsAreas()) {
            float x1 = door.getX1();
            float y1 = door.getY1();
            float x2 = door.getWidth()*UNIT_SCALE;
            float y2 = door.getHeight()*UNIT_SCALE;

            GamePanel.BODY_DEF.position.set(x1, y1);
            GamePanel.BODY_DEF.fixedRotation = true;
            Body body = world.createBody(GamePanel.BODY_DEF);
            body.setUserData("DOOR");
    
            GamePanel.FIXTURE_DEF.filter.categoryBits = GamePanel.BIT_GROUND;
            GamePanel.FIXTURE_DEF.filter.maskBits     = -1;
    
            ChainShape shape = new ChainShape();
            shape.createChain(new float[]{0, 0,               
                                        0, y2,
                                        x2, y2,
                                        x2, 0,
                                        0, 0}); 
            GamePanel.FIXTURE_DEF.shape = shape;
            body.createFixture(GamePanel.FIXTURE_DEF);
            shape.dispose();
            Vector2 size = new Vector2(x2, y2);
            doors.add(new Door(body, door.getName(),size));
        }
    }

    /**
     * Gets the current map.
     *
     * @return The current Map object.
     */
    public Map getCurrentMap() {
        return currentMap;
    }

    public MapType getCurrentMapType() {
        return currentMapType;
    }

    public Boolean openDoor(String doorName){
        for (Door d: doors){
            if (Objects.equals(doorName, d.getName())){
                d.removeDoor(world);
                doors.removeValue(d, true);
                return true;
            }
        }
        return false;
    }
    public Array<Door> getDoors() {
        return doors;
    }
    public Array<Body> getBodies() {
        return bodies;
    }
    public void spawnTaskBoard() {
        Array<Borders> taskBoards = currentMap.getBorders("TaskBoard");
        if (taskBoards == null || taskBoards.isEmpty()) {
            Gdx.app.debug(TAG, "[WARNING] No TaskBoard borders found in the current map.");
            this.taskBoard = null;
            return;
        }

        for (Borders b : taskBoards) {
            float x = b.getX1();
            float y = b.getY1();
            float width = b.getWidth()*UNIT_SCALE;
            float height = b.getHeight()*UNIT_SCALE;
            this.taskBoard = new TaskBoard(x, y, width, height, assetManager);
        }
        
    }
    public TaskBoard getTaskBoard() {
        //spawnTaskBoard();
        if(currentMapType == MapType.MAP_START) {
            return taskBoard;
        }
        else return null;
    }
}
