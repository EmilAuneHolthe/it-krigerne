package inf112.skeleton.model.map;

import java.util.EnumMap;

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
import inf112.skeleton.model.entity.player.CharacterType;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    private World world;
    private Array<Body> bodies;
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
        mapCache = new EnumMap<>(MapType.class);
        listners = new Array<>();
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
    private void spawnDoors(){
        for (final Borders door : currentMap.getDoorsAreas()) {
            GamePanel.BODY_DEF.position.set(door.getX1(), door.getY1());
            GamePanel.BODY_DEF.fixedRotation = true;
            final Body body = world.createBody(GamePanel.BODY_DEF);
            body.setUserData("DOOR");
            GamePanel.FIXTURE_DEF.filter.categoryBits = GamePanel.BIT_GROUND;
            GamePanel.FIXTURE_DEF.filter.maskBits = -1;

            final ChainShape cShape = new ChainShape();
            float[] doorVertices = new float[]{door.getX1(), door.getY1(), door.getX2(), door.getY2()};
            cShape.createChain(doorVertices);
            GamePanel.FIXTURE_DEF.shape = cShape;
            body.createFixture(GamePanel.FIXTURE_DEF);
            cShape.dispose();
            bodies.add(body);
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
}
