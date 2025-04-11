package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemSpawn;

public class PlayerInteractions {
  private final float attackRange;
  public PlayerInteractions(GamePanel context) {
    this.attackRange = 2.0f;
  }
  public void attackEnemy(Player player, Array<Enemy> enemies) {
    if(player.canAttack) {
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
}
  private Array<String> distanceToPlayer(Player player, Array<Enemy> enemies) {
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
public void pickUpItem(Player player, Array<Item> items) {
  for (Item item : items) {
    if (player.getPosition().dst(item.getPosition()) < 1.0f) {
      player.pickUpItem(item.getItemType());
      item.remove();
      items.removeValue(item, true);
    }
  }
}
}