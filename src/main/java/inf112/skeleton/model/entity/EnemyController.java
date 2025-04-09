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
    for (Enemy enemy : enemies) {
      Vector2 enemyPosition = enemy.getPosition();
      Vector2 playerPosition = player.getPosition();
      // Calculate the distance between the enemy and the player
      float distance = enemyPosition.dst(playerPosition);

      if (distance < 8) {
        // Calculate relative position
        float dx = playerPosition.x - enemyPosition.x;
        float dy = playerPosition.y - enemyPosition.y;
        float angle = (float) Math.atan2(dy, dx);
        float angleDegrees = (float) Math.toDegrees(angle);
        
        // Normalize angle to 0-360 range
        if (angleDegrees < 0) {
            angleDegrees += 360;
        }
        
        
        // Set direction based on angle
        if (angleDegrees >= 315 || angleDegrees < 45) {
          enemy.setDirection("Right");
          System.out.println("Right");
        } else if (angleDegrees >= 45 && angleDegrees < 135) {
          enemy.setDirection("Up");
          System.out.println("Up");
        } else if (angleDegrees >= 135 && angleDegrees < 225) {
          enemy.setDirection("Left");
          System.out.println("Left");
        } else {
          enemy.setDirection("Front");
          System.out.println("Down");
        }
        enemy.moveEnemy(playerPosition.x, playerPosition.y);
        if(distance < 0.8) {
          player.takeDamage(enemy.getDamage());
        }
      }
      else {
        enemy.setLinearVelocity(0, 0);
        enemy.setDirection("Front");
      }
    }
  }
}