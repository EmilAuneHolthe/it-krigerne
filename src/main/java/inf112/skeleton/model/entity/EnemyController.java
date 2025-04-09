package inf112.skeleton.model.entity;

import com.badlogic.gdx.utils.Array;

import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.audio.AudioTypes;
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
        // Move towards player
        enemy.moveEnemy(playerPosition.x, playerPosition.y);

        // Check if the enemy is within attack range, then player take damage
        if(distance < 0.8) {
          player.playerTakeDamage(enemy);

        }
      } else {
        // Stop moving if player is out of range
        enemy.setLinearVelocity(0, 0);
      }
    }
  }
}