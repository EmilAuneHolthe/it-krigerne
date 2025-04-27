package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.utils.Array;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;

public class PlayerInteractions {
  private final float attackRange;
  private final GamePanel context;
 
  
  
  public PlayerInteractions(GamePanel context) {
    this.attackRange = 2.0f;
    this.context = context;
  }



  public void attackEnemy(Player player, Array<Enemy> enemies) {

    if(player.canAttack) {
      context.getAudioHandler().playAudio(AudioTypes.ATTACK);
      Array<String> enemyName = distanceToPlayer(player, enemies);
      if (enemyName != null) {
        for(Enemy enemy : enemies) {
            if (enemyName.contains(enemy.getName(), false)) {
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
        if(distance < attackRange) {
            enemyNames.add(enemy.getName());
        }
    }
    if (enemyNames.size > 0) {
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