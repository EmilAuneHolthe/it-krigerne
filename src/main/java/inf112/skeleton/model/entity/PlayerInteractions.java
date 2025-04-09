package inf112.skeleton.model.entity;

import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;

public class PlayerInteractions {
  private final float attackRange;
  public PlayerInteractions(GamePanel context) {
    this.attackRange = 2.0f;
  }
  public void attackEnemy(Player player, Array<Enemy> enemies) {
    Array<String> enemyName = distanceToPlayer(player, enemies);
    if (enemyName != null) {
        for(Enemy enemy : enemies) {
            if (enemyName.contains(enemy.getName(), false)) {
                System.out.println("Enemy " + enemy.getName() + " is within range!");
                enemy.takeDamage(player.attack());
            }
          }
  }
}
  private Array<String> distanceToPlayer(Player player,
  Array<Enemy> enemies) {
    Array<String> enemyNames = new Array<>();
    for (Enemy enemy : enemies) {
        float distance = enemy.getPosition().dst(player.getPosition());
        System.out.println("Distance to player: " + distance + enemy.getName());
        if(distance < attackRange) {
            enemyNames.add(enemy.getName());
        }
    }
    if (enemyNames.size > 0) {
      System.out.println("Enemies within range: " + enemyNames);
        return enemyNames;
    }
    return null;

}
}