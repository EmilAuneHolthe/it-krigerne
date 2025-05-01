package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.utils.Array;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;

public class PlayerInteractions {
  private final float attackRange;
  private final GamePanel context;
  private final Player player;
  
  
  public PlayerInteractions(GamePanel context, Player player) {
    this.player = player;
    this.attackRange = 2.0f;
    this.context = context;
  }

  public void attackEnemy(Player player, Array<Enemy> enemies) {
    if (player.canAttack) {
        Array<String> enemyNames = distanceToPlayer(player, enemies);
        context.getAudioHandler().playAudio(AudioTypes.ATTACK);
        if (enemyNames != null && enemyNames.size > 0) {
            for (Enemy enemy : enemies) {
                if (enemyNames.contains(enemy.getName(), false)) {
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
}