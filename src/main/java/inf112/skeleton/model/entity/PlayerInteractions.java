package inf112.skeleton.model.entity;

import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;

public class PlayerInteractions {
  private final float attackRange;
  public PlayerInteractions(GamePanel context) {
    this.attackRange = 2.0f;
  }
  public void attackEnemy(Player player, Array<Enemy> enemies) {
    String enemyName = distanceToPlayer(player, enemies);
    if (enemyName != null) {
        for(Enemy enemy : enemies) {
            if (enemy.getName().equals(enemyName)) {
                System.out.println("Enemy " + enemy.getName() + " is within range!");
                enemy.takeDamage(player.attack());
            }
          }
  }
}
  private String distanceToPlayer(Player player,
  Array<Enemy> enemies) {
    for (Enemy enemy : enemies) {
        float distance = enemy.getPosition().dst(player.getPosition());
        System.out.println("Distance to player: " + distance + enemy.getName());
        if(distance < attackRange) {
            return enemy.getName();
        }
    }
    return null;

}
}