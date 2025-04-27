package inf112.skeleton.model.map;

import java.util.ArrayList;

import javax.swing.border.Border;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.entity.door.Door;
import inf112.skeleton.model.entity.enemy.EnemySpawn;
import inf112.skeleton.model.entity.item.ItemSpawn;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.CharacterType;

public class Map {
  private final TiledMap tiledMap;
  public static final String TAG = Map.class.getSimpleName();
  private Array<CollisionArea> collisionAreas;
  private final ArrayList<EnemySpawn>  enemySpawn;
  private final ArrayList<ItemSpawn> itemSpawn;
  
  public Map(TiledMap tiledMap) {
    this.collisionAreas = new Array<>();
    this.tiledMap = tiledMap;
    enemySpawn = findEnemySpawn();
    itemSpawn = findItemSpawn();
    getCollisionLayer();
  }

 private void getCollisionLayer() {
    MapLayer collisionLayer = tiledMap.getLayers().get("Collision");
    
    if (collisionLayer == null) {
      Gdx.app.error(TAG, "Collision layer not found! Ensure 'collision' layer exists in the map.");
      return;
    
    }
    final MapObjects mapObjects = collisionLayer.getObjects();
    if(mapObjects == null) {
      Gdx.app.error(TAG, "No collision objects found in 'collision' layer!");
      return;
    }

    for(final MapObject mapObject : mapObjects) {
      if(mapObject instanceof RectangleMapObject) {
        final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
        final Rectangle rectangle = rectangleMapObject.getRectangle();
        final float[] rectVertices = new float[10];
        //rectangle left bottom
        rectVertices[0] = 0;
        rectVertices[1] = 0;

        //rectangle left top
        rectVertices[2] = 0;
        rectVertices[3] = rectangle.height;

        //rectangle right top
        rectVertices[4] = rectangle.width;
        rectVertices[5] = rectangle.height;

        //rectangle right bottom
        rectVertices[6] = rectangle.width;
        rectVertices[7] = 0;

        //rectangle left bottom
        rectVertices[8] = 0;
        rectVertices[9] = 0;
        collisionAreas.add(new CollisionArea(rectangle.x, rectangle.y, rectVertices));
      } else if(mapObject instanceof PolylineMapObject) {
        final PolylineMapObject polylineMapObject = (PolylineMapObject) mapObject;
        final Polyline polyLine = polylineMapObject.getPolyline();
        collisionAreas.add(new CollisionArea(polyLine.getX(), polyLine.getY(), polyLine.getVertices()));
       
      } else {
        Gdx.app.debug(TAG, "Collision object not supported: " + mapObject);
      }
    }
   
  }
  
  public Array<CollisionArea> getColissionAreas() {
    return collisionAreas;
  }
  private Vector2 findPlayerSpawnVector2(String name) {
    MapObjects objects = tiledMap.getLayers().get(name).getObjects();
          for (final MapObject object : objects) {
            final RectangleMapObject spawn = (RectangleMapObject) object;
            final Rectangle rectangle = spawn.getRectangle();
            Gdx.app.debug(TAG, "Player spawn found at: " + rectangle.x + ", " + rectangle.y);
            return new Vector2(rectangle.x, rectangle.y);
          }
          return new Vector2(0, 0);
        }

        private ArrayList<EnemySpawn> findEnemySpawn() {
          ArrayList<EnemySpawn> enemySpawns = new ArrayList<>();
          MapObjects objects = tiledMap.getLayers().get("Enemy").getObjects();
          for (final MapObject object : objects) {
              final RectangleMapObject spawn = (RectangleMapObject) object;
              final Rectangle rectangle = spawn.getRectangle();
              String name = spawn.getName();
              CharacterType characterType = CharacterType.valueOf(spawn.getProperties().get("Type").toString().toUpperCase());
              Vector2 position = new Vector2(rectangle.x, rectangle.y);
              enemySpawns.add(new EnemySpawn(position, name, characterType ));
          }
          return enemySpawns;
        }
        
        private ArrayList<ItemSpawn> findItemSpawn() {
          ArrayList<ItemSpawn> itemSpawns = new ArrayList<>();
          MapObjects objects = tiledMap.getLayers().get("Items").getObjects();
          for (final MapObject object : objects) {
            final RectangleMapObject spawn = (RectangleMapObject) object;
            final Rectangle rectangle = spawn.getRectangle();
            Gdx.app.debug(TAG, "Item spawn found at: " + rectangle.x + ", " + rectangle.y);
            ItemType itemType = ItemType.valueOf(spawn.getProperties().get("Type").toString().toUpperCase());
            Vector2 position = new Vector2(rectangle.x, rectangle.y);
            itemSpawns.add(new ItemSpawn(position, itemType));
          }
          return itemSpawns;
        }

  public Vector2 getPlayerSpawn() {
    return findPlayerSpawnVector2("Player");
  }

  public TiledMap getTiledMap() {
    return tiledMap;
}
public ArrayList<EnemySpawn> getEnemySpawn() {
  return enemySpawn;
}
public Vector2 getBossSpawn(){
  return findPlayerSpawnVector2("Boss");
}
public ArrayList<ItemSpawn> getItemSpawn() {
  return itemSpawn;
}
public ArrayList<Borders> getBorders(String layer){
  ArrayList<Borders> borders = new ArrayList<>();
  MapLayer collisionLayer = tiledMap.getLayers().get(layer);
  if (collisionLayer == null) {
    Gdx.app.error(TAG, "Collision layer not found! Ensure 'collision' layer exists in the map.");
    return null;
  }
  final MapObjects mapObjects = collisionLayer.getObjects();
  if(mapObjects == null) {
    Gdx.app.error(TAG, "No collision objects found in 'collision' layer!");
    return null;
  }
  for(final MapObject mapObject : mapObjects) {
    if(mapObject instanceof RectangleMapObject) {
      final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
      final Rectangle rectangle = rectangleMapObject.getRectangle();
      borders.add(new Borders(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, mapObject.getName()));
    }
  }
  return borders;
}
public ArrayList<Borders> getDoorsAreas() {
  ArrayList<Borders> doors = new ArrayList<>();
  try{
  for(final MapObject mapObject : tiledMap.getLayers().get("Door").getObjects()) {
    if (mapObject instanceof RectangleMapObject) {
      final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
      final Rectangle rectangle = rectangleMapObject.getRectangle();
      doors.add(new Borders(rectangle.x, rectangle.y, rectangle.width, rectangle.height, mapObject.getName()));
    }
  }
  } catch (NullPointerException e) {
    Gdx.app.error(TAG, "Door layer not found! Ensure 'Door' layer exists in the map.");
  }
  return doors;
}
}
