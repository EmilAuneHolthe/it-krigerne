package inf112.skeleton.model.map;

import java.util.EnumMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.model.GamePanel;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    public static final String MapType = null;

    private  World world;
    private Array<Body> bodies;
    private AssetManager assetManager;
    private MapType currentMapType;
    private Map currentMap;
    private final EnumMap<MapType, Map> mapCache;
    private final Array<MapListener> listners;

    public MapManager(GamePanel context) {
        currentMapType = null;
        currentMap = null;
        world = context.getWorld();
        assetManager = context.getAssetManager();
        bodies = new Array<Body>();
        mapCache = new EnumMap<MapType, Map>(MapType.class);
        listners = new Array<MapListener>();


    }
    /**
     * Adds a listener to the map manager.
     * 
     * @param listener The listener to add.
     */

    public void addListener(final MapListener listener) {
        listners.add(listener);
    }
   
    /**
     * Set the map to the given type.
     * Loads the map if it has not been loaded before.
     * 
     * @param listener The listener to remove.
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
        if (currentMapType == null) {
            Gdx.app.debug(TAG, "Creatign new map: " + type);
            final TiledMap tiledMap = assetManager.get(type.getFilePath(), TiledMap.class);
            currentMap = new Map(tiledMap);
            mapCache.put(type, currentMap);
        }

        spawnCollisionsAreas();

        for (final MapListener listener : listners) {
            listener.mapChanged(currentMap);
        }

    }

    private void destroyCollisionAreas() {
        for (final Body body : bodies) {
            if("GROUND".equals(body.getUserData())) {
                world.destroyBody(body);
            }
        }
        bodies.clear();
    }

    private void spawnCollisionsAreas() {
        GamePanel.resetBodyAndFixtureDefinition();
        for (final CollisionArea collisionArea : currentMap.getColissionAreas()) {

            // creates room
            GamePanel.BODY_DEF.position.set(collisionArea.getX(), collisionArea.getY());
            GamePanel.BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(GamePanel.BODY_DEF);
            body.setUserData("GROUND");

            GamePanel.FIXTURE_DEF.filter.categoryBits = GamePanel.BIT_GROUND;
            GamePanel.FIXTURE_DEF.filter.maskBits = -1;
            final ChainShape cShape = new ChainShape();
            cShape.createChain(collisionArea.getVertices());
            GamePanel.FIXTURE_DEF.shape = cShape;
            body.createFixture(GamePanel.FIXTURE_DEF);
            cShape.dispose();
        }
    }

    /**
     * Returns the current map.
     * 
     * @return The current map.
     */
    public Map getCurrentMap() {
        return currentMap;
    }

    

}


   