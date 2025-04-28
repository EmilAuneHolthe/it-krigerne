package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import java.util.EnumMap;
import java.util.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer.Task;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.door.Door;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    private World world;
    private Array<Body> bodies;
    private Array<Door> doors;
    private TaskBoard taskBoard;
    private AssetManager assetManager;
    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache;
    private final Array<MapListener> listners;

    /**
     * Oppretter en ny instans av MapManager med tilgang til world og assetManager via GamePanel.
     *
     * @param context Spillets GamePanel som gir tilgang til world og assets.
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
     * Legger til en lytter som får beskjed hver gang kartet endres.
     *
     * @param listener Lytteren som skal legges til.
     */
    public void addListener(final MapListener listener) {
        listners.add(listener);
    }

    /**
     * Setter spillet til et nytt kart (MapType).
     * Hvis kartet allerede er lastet inn, hentes det fra cache.
     * Eksisterende kollisjonsobjekter fjernes og nye opprettes.
     *
     * @param type Karttypen som skal lastes.
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
     * Fjerner alle kollisjonsområder som har blitt laget i world.
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
     * Oppretter kollisjonsobjekter i verden basert på CollisionAreas i det nåværende kartet.
     * Sikrer at duplikate/nære punkter ikke fører til Box2D-krasj.
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
                doors.add(new Door(x1, x2, world, body, door.getName(), assetManager, x2, y2));
            }
        }
    private void spawnCollisionsAreas() {
        GamePanel.resetBodyAndFixtureDefinition();
        for (final CollisionArea collisionArea : currentMap.getColissionAreas()) {
            GamePanel.BODY_DEF.position.set(collisionArea.getX(), collisionArea.getY());
            GamePanel.BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(GamePanel.BODY_DEF);
            body.setUserData("GROUND");
            GamePanel.FIXTURE_DEF.filter.categoryBits = GamePanel.BIT_GROUND;
            GamePanel.FIXTURE_DEF.filter.maskBits = -1;

            float[] original = collisionArea.getVertices();
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
     * Henter det nåværende kartet.
     *
     * @return Nåværende Map-objekt.
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
                d.removeDoor();
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
         return taskBoard;
    }
}
