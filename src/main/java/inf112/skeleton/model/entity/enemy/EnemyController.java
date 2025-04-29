package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.utils.Array;


import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.entity.player.Player;

public class EnemyController {
  private Array<Enemy> enemies;
  private final Player player;
  public EnemyController(Array<Enemy> enemies, Player player) {
    this.enemies = enemies;
    this.player = player;
    // Constructor logic here
  }

  public void updateEnemies(Array<Enemy> newEnemies) {
    this.enemies = newEnemies;
  }
  
  public Array<Enemy> getEnemies() {
    return enemies;
  }
  
  public void sight() {
    System.out.println("Enemy sight");
    for (Enemy enemy : enemies) {
      System.out.println("Enemy sight");
      Vector2 enemyPosition = enemy.getPosition();
      Vector2 playerPosition = player.getPosition();
      // Calculate the distance between the enemy and the player
      float distance = enemyPosition.dst(playerPosition);

      if (distance < enemy.getSightRange()) {


        
        enemy.moveEnemy(playerPosition.x, playerPosition.y);

        // Check if the enemy is within attack range, then player take damage
        if(distance < 0.8) {
          player.playerTakeDamage(enemy);

        }
      }
      else {
        enemy.setLinearVelocity(0, 0);
      }
    }
  }
}