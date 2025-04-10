package inf112.skeleton.model.map;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.entity.Enemy;
import inf112.skeleton.model.entity.Player;

public class MapChanger {
  public void removeObjects(World world, Map map, Array<Enemy> enemies) {
    for (Enemy enemy : enemies) {
      world.destroyBody(enemy.getBody());
      enemy.dispose();
    }
  }
  public void movePlayer(World world, Map map, Player player) {
    player.setSpawn(map.getPlayerSpawn().x, map.getPlayerSpawn().y);
  }
}
