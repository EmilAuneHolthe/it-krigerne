package inf112.skeleton.app.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Map {
  private final TiledMap tiledMap;
  public static final String TAG = Map.class.getSimpleName();
  private final Array<CollisonArea> colissionAreas;
  
  public Map(TiledMap tiledMap) {
    this.tiledMap = tiledMap;
    colissionAreas = new Array<CollisonArea>();
    getCollisionLayer();
      }
    
      private void getCollisionLayer() {
       final MapLayer collisionLayer = tiledMap.getLayers().get("collision");
       if (collisionLayer == null) {
         Gdx.app.debug(TAG, "Map must have a collision layer");
         return;
       }
       final MapObjects objects = collisionLayer.getObjects();
       if(objects == null) {
         Gdx.app.debug(TAG, "Collision layer must have objects");
         return;
       }

       for (final MapObject object : objects) {
         if (object instanceof RectangleMapObject) {
          final RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
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
          colissionAreas.add(new CollisonArea(rectangle.x, rectangle.y, rectVertices));
         } else if( object instanceof PolylineMapObject) {
          final PolylineMapObject polynineMapObject = (PolylineMapObject) object;
          final Polyline polyline = polynineMapObject.getPolyline();
          colissionAreas.add(new CollisonArea(polyline.getX(), polyline.getY(), polyline.getVertices()));
         } else {
          Gdx.app.debug(TAG, "Collision object not supported" + object);
         }
       }
      }
      public Array<CollisonArea> getColissionAreas() {
        return colissionAreas;
      }
}
