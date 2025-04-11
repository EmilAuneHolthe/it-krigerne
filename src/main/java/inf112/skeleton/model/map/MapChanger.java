package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyController;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.view.screen.GameScreen;

public class MapChanger {

  public void removeObjects(World world, Map map, Array<Enemy> enemies) {
    for (Enemy enemy : enemies) {
      world.destroyBody(enemy.getBody());
      enemy.dispose();
    }
  }
  public void movePlayer(World world, Map map, Player player) {
    player.setSpawn(map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
  }
}
