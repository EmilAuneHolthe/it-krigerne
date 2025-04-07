package inf112.skeleton.model.entity;

import com.badlogic.gdx.utils.Array;

import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class EnemyController {
  private final GamePanel context;
  private final World world;
  private final Array<Enemy> enemies;
  private final Player player;
  public EnemyController(GamePanel context, World world, Array<Enemy> enemies, Player player) {
    this.context = context;
    this.world = world;
    this.enemies = enemies;
    this.player = player;
    // Constructor logic here
  }
  public void sight() {
    System.out.println("Enemy sight");
    for (Enemy enemy : enemies) {
      Vector2 enemyPosition = enemy.getPosition();
      Vector2 playerPosition = player.getPosition();
      // Calculate the distance between the enemy and the player
      float distance = enemyPosition.dst(playerPosition);

      if (distance < 5) {
        float x = playerPosition.x - enemyPosition.x;
        float y = playerPosition.y - enemyPosition.y;
        float angle = (float) Math.atan2(y, x);
        float angleDegrees = (float) Math.toDegrees(angle);
        if (angleDegrees >= -45 && angleDegrees < 45) {
          enemy.setDirection("Right");
        } else if (angleDegrees >= 45 && angleDegrees < 135) {
          enemy.setDirection("Up");
        } else if (angleDegrees >= 135 || angleDegrees < -135) {
          enemy.setDirection("Left");
        } else {
          enemy.setDirection("Down");
        }
        enemy.moveEnemy(x, y);
      }
    }
  }
}